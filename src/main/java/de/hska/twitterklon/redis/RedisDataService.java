package de.hska.twitterklon.redis;

import de.hska.twitterklon.api.transferobjects.PostDto;

import java.util.List;
import java.util.Optional;

public interface RedisDataService {
    Optional<String> createSession(String userName, String password, int sessionDuration);
    Optional<String> getUserNameFromSession(String sessionUUID);

    void addFollower(String follower, String following);
    void removeFollower(String follower, String following);
    List<String> getFollower(String UserName);
    List<String> getFollowing(String UserName);

    List<PostDto> getLastPosts(String UserName, int postCount);

    List<PostDto> getLatestTimeline(int postCount);
    List<PostDto> getLatestTimeline(String Username, int postCount);
}
