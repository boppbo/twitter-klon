package de.hska.twitterklon.redis;

import de.hska.twitterklon.api.transferobjects.PostDto;
import de.hska.twitterklon.redis.repositories.UserRelationshipRepository;
import de.hska.twitterklon.redis.repositories.UserRepository;
import de.hska.twitterklon.redis.repositories.entities.UserEntity;
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
    private final StringRedisTemplate redisTemplate;
    //Auth:<UUID> (String of Username
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
    //    •	Content (String)
    //    •	TimeStamp (String)

    private String buildAuthKey(String authKey) {
        return AUTH_KEY + ":" + authKey;
    }

    public RedisDataServiceImpl(StringRedisTemplate redisTemplate, UserRepository users, UserRelationshipRepository userRel) {
        this.redisTemplate = redisTemplate;
        this.opsForAuth = redisTemplate.opsForValue();

        this.users = users;
        this.userRel = userRel;
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
    public Optional<String> createSession(String userName, String password, int sessionDuration) {
        if (StringUtils.isEmpty(userName))
            throw new IllegalArgumentException("userName");
        if(StringUtils.isEmpty(password))
            throw new IllegalArgumentException("password");

        UserEntity u = this.users.findOne(userName);
        if (u == null || !u.getPassword().equals(password))
            return Optional.empty();


        String authKey = UUID.randomUUID().toString();
        this.opsForAuth.set(buildAuthKey(authKey), u.getUserName(), sessionDuration, TimeUnit.SECONDS);

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
    public List<PostDto> getLastPosts(String userName, int postCount, int skipCount) {
        return null;
    }

    @Override
    public List<PostDto> getLatestTimeline(String userName, int postCount, int skipCount) {
        return null;
    }
}
