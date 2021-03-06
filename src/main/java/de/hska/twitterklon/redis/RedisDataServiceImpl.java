package de.hska.twitterklon.redis;

import de.hska.twitterklon.api.transferobjects.PostDto;
import de.hska.twitterklon.redis.repositories.PostRepository;
import de.hska.twitterklon.redis.repositories.UserRelationshipRepository;
import de.hska.twitterklon.redis.repositories.UserRepository;
import de.hska.twitterklon.redis.repositories.entities.UserEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class RedisDataServiceImpl implements RedisDataService {
    private static final String AUTH_KEY = "Auth";
    private static final String GLOBAL_TIMELINE_KEY = "Timeline:Global";
    private static final String PROFILE_TIMELINE_KEY = "Timeline:Profile";
    private static final String USER_TIMELINE_KEY = "Timeline:User";

    private final StringRedisTemplate redisTemplate;
    private final RedisTemplate<String, PostDto> redisMessagingTemplate;

    //Auth:<UUID> (String of Username)
    private final ValueOperations<String, String> opsForAuth;

    //Users (Hash)
    // Key: UserName (String)
    // Data:
    //    •	Auth (String of uuid session secret)
    //    •	Password (String)
    private final UserRepository users;
    //Users:<UserName>:Follows (Set of <UserName>)
    //Users:<UserName>:FollowedBy (Set of <UserName>)
    private final UserRelationshipRepository userRel;
    //Posts (Hash)
    // Key: postUUID (String)
    // Data:
    //    •	Content (String)
    //    •	TimeStamp (String)
    //    •	UserName (String)
    private final PostRepository posts;
    //Timeline:Global (List of <postUUID>)
    //Timeline:Profile:<UserName> (List of <postUUID>)
    //Timeline:User:<UserName> (List of <postUUID>)
    private final ListOperations<String, String> listOps;

    @Value("${appConfig.sessionTimeout}")
    private int sessionTTL;

    private String buildAuthKey(String authKey) {
        return AUTH_KEY + ":" + authKey;
    }
    private String buildPostsKey(String userName) {
        return PROFILE_TIMELINE_KEY + ":" + userName;
    }
    private String buildTimelineKey(String userName) {
        return USER_TIMELINE_KEY + ":" + userName;
    }
    private List<PostDto> getPostsFromId(List<String> postIds) {
        ArrayList<PostDto> result = new ArrayList<>();
        this.posts.findAll(postIds).forEach(result::add);
        return result;
    }

    public RedisDataServiceImpl(StringRedisTemplate redisTemplate, @Qualifier("messaging") RedisTemplate<String, PostDto> redisMessagingTemplate, UserRepository users, UserRelationshipRepository userRel, PostRepository posts) {
        this.redisTemplate = redisTemplate;
        this.opsForAuth = redisTemplate.opsForValue();
        this.listOps = redisTemplate.opsForList();
        this.redisMessagingTemplate = redisMessagingTemplate;

        this.users = users;
        this.userRel = userRel;
        this.posts = posts;
    }

    @Override
    public void createUser(String userName, String password) {
        if (StringUtils.isEmpty(userName))
            throw new IllegalArgumentException("userName");
        if(StringUtils.isEmpty(password))
            throw new IllegalArgumentException("password");
        if (users.exists(userName))
            throw new KeyAlreadyExistsException();

        this.users.save(new UserEntity(userName, password, null));
    }

    @Override
    public List<String> searchUser(String searchTerm, int resultCount, int skipCount) {
        searchTerm = searchTerm.trim();

        if (StringUtils.isEmpty(searchTerm))
            throw new IllegalArgumentException("searchTerm");

        return this.users.searchUser(searchTerm, resultCount, skipCount);
    }

    @Override
    public Optional<String> createSession(String userName, String password) {
        if (StringUtils.isEmpty(userName))
            throw new IllegalArgumentException("userName");
        if(StringUtils.isEmpty(password))
            throw new IllegalArgumentException("password");

        UserEntity u = this.users.findOne(userName);
        if (u == null || !u.getPassword().equals(password))
            return Optional.empty();


        String authKey = UUID.randomUUID().toString();
        this.opsForAuth.set(buildAuthKey(authKey), u.getUserName(), sessionTTL, TimeUnit.SECONDS);

        u.setAuthKey(authKey);
        return Optional.of(this.users.save(u).getAuthKey());
    }

    @Override
    public Optional<String> getUserNameFromSession(String sessionUUID) {
        if (StringUtils.isEmpty(sessionUUID))
            return Optional.empty();

        String userName = this.opsForAuth.get(buildAuthKey(sessionUUID));
        if (StringUtils.isEmpty(userName))
            return Optional.empty();

        UserEntity user = this.users.findOne(userName);
        if (user == null || !user.getAuthKey().equals(sessionUUID))
            return Optional.empty();

        //Reset session timeout.
        this.opsForAuth.set(buildAuthKey(user.getAuthKey()), user.getUserName(), sessionTTL, TimeUnit.SECONDS);

        return Optional.of(user.getUserName());
    }

    @Override
    public void removeSession(String sessionUUID) {
        this.redisTemplate.delete(buildAuthKey(sessionUUID));
    }

    @Override
    public void addFollower(String follower, String following) {
        this.userRel.addFollower(follower, following);
    }

    @Override
    public void removeFollower(String follower, String following) {
        this.userRel.removeFollower(follower, following);
    }

    @Override
    public List<String> getFollower(String userName) {
        return new ArrayList<>(this.userRel.getFollower(userName));
    }

    @Override
    public List<String> getFollowing(String userName) {
        return new ArrayList<>(this.userRel.getFollowing(userName));
    }

    @Override
    public void addPost(String content) {
        if (StringUtils.isEmpty(content))
            throw new IllegalArgumentException("post");

        PostDto post = this.posts.save(new PostDto(content));
        this.listOps.leftPush(buildPostsKey(post.getUserName()), post.getId());
        this.listOps.leftPush(buildTimelineKey(post.getUserName()), post.getId());
        this.listOps.leftPush(GLOBAL_TIMELINE_KEY, post.getId());

        for (String follower: this.userRel.getFollower(post.getUserName())) {
            this.listOps.leftPush(buildTimelineKey(follower), post.getId());
            this.redisMessagingTemplate.convertAndSend("timeline/" + follower, post);
        }
    }

    @Override
    public List<PostDto> getLastPosts(String userName, int postCount, int skipCount) {
        List<String> ids = this.listOps.range(buildPostsKey(userName), skipCount, skipCount + postCount - 1);
        return this.getPostsFromId(ids);
    }

    @Override
    public List<PostDto> getLatestTimeline(String userName, int postCount, int skipCount) {
        String key = StringUtils.isEmpty(userName)
                ? GLOBAL_TIMELINE_KEY
                : buildTimelineKey(userName);
        List<String> ids = this.listOps.range(key, skipCount, skipCount + postCount - 1);
        return this.getPostsFromId(ids);
    }
}
