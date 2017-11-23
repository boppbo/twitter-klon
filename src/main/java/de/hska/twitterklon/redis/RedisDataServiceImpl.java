package de.hska.twitterklon.redis;

import de.hska.twitterklon.api.transferobjects.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RedisDataServiceImpl implements RedisDataService {

    private final StringRedisTemplate redisTemplate;
    //Auth:<UUID> (String of Username
    //Users:<UserName> (Hash)
    //    •	Auth (String of uuid session secret)
    //    •	Password (String)
    //Users:<UserName>:Follows (Set of <UserName>)
    //Users:<UserName>:FollowedBy (Set of <UserName>)

    //Posts:<UserName> (List of UUIDs)
    //Posts:<UserName>:<UUID> (Hash)
    //    •	Content (String)
    //    •	TimeStamp (String)
    //Timeline (List of String, Format: “<authorUsername>:<UUID>” für globale Timeline)
    //Timeline:<UserName> (List of String, Format: “<authorUsername>:<UUID>”)

    @Autowired
    public RedisDataServiceImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void createUser(String userName, String password) {

    }

    @Override
    public Optional<String> createSession(String userName, String password, int sessionDuration) {
        return null;
    }

    @Override
    public Optional<String> getUserNameFromSession(String sessionUUID) {
        return null;
    }

    @Override
    public void removeSession(String sessionUUID) {

    }

    @Override
    public void addFollower(String follower, String following) {

    }

    @Override
    public void removeFollower(String follower, String following) {

    }

    @Override
    public List<String> getFollower(String userName) {
        return null;
    }

    @Override
    public List<String> getFollowing(String userName) {
        return null;
    }

    @Override
    public List<PostDto> getLastPosts(String userName, int postCount) {
        return null;
    }

    @Override
    public List<PostDto> getLatestTimeline(int postCount) {
        return null;
    }

    @Override
    public List<PostDto> getLatestTimeline(String userName, int postCount) {
        return null;
    }
}
