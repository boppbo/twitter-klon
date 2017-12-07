package de.hska.twitterklon.redis.repositories;

import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class UserRelationshipRepository {
    private static final String USERS = "Users";
    private static final String FOLLOWS = "Follows";
    private static final String FOLLOWING = "Following";
    private static final String SEPARATOR = ":";

    private final SetOperations<String, String> setOps;

    private String buildFollows(String userName) {
        return USERS + SEPARATOR + userName + SEPARATOR + FOLLOWS;
    }
    private String buildFollowing(String userName) {
        return USERS + SEPARATOR + userName + SEPARATOR + FOLLOWING;
    }

    public UserRelationshipRepository(StringRedisTemplate redisTemplate) {
        this.setOps = redisTemplate.opsForSet();
    }

    public void addFollower(String follower, String following) {
        this.setOps.add(buildFollows(follower), following);
        this.setOps.add(buildFollowing(following), follower);
    }

    public void removeFollower(String follower, String following) {
        this.setOps.remove(buildFollows(follower), following);
        this.setOps.remove(buildFollowing(following), follower);
    }

    public Set<String> getFollower(String userName) {
        return this.setOps.members(buildFollows(userName));
    }

    public Set<String> getFollowing(String userName) {
        return this.setOps.members(buildFollowing(userName));
    }
}
