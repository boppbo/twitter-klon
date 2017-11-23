package de.hska.twitterklon.redis;

import de.hska.twitterklon.api.transferobjects.PostDto;

import java.util.List;
import java.util.Optional;

public interface RedisDataService {
    void createUser(String userName, String password);
    Optional<String> createSession(String userName, String password, int sessionDuration);
    Optional<String> getUserNameFromSession(String sessionUUID);
    void  removeSession(String sessionUUID);

    void addFollower(String follower, String following);
    void removeFollower(String follower, String following);
    List<String> getFollower(String userName);
    List<String> getFollowing(String userName);

    List<PostDto> getLastPosts(String userName, int postCount);

    List<PostDto> getLatestTimeline(int postCount);
    List<PostDto> getLatestTimeline(String userName, int postCount);
}
