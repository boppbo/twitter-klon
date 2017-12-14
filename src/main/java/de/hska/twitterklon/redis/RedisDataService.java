package de.hska.twitterklon.redis;

import de.hska.twitterklon.api.transferobjects.PostDto;

import java.util.List;
import java.util.Optional;

public interface RedisDataService {
    void createUser(String userName, String password);
    List<String> searchUser(String searchTerm, int resultCount, int skipCount);
    Optional<String> createSession(String userName, String password, int sessionDuration);
    Optional<String> getUserNameFromSession(String sessionUUID);
    void  removeSession(String sessionUUID);

    void addFollower(String follower, String following);
    void removeFollower(String follower, String following);
    List<String> getFollower(String userName);
    List<String> getFollowing(String userName);

    void addPost(String content);
    List<PostDto> getLastPosts(String userName, int postCount,int skipCount);
    default List<PostDto> getLastPosts(String userName, int postCount) {
        return this.getLastPosts(userName, postCount, 0);
    }

    List<PostDto> getLatestTimeline(String userName, int postCount, int skipCount);
    default List<PostDto> getLatestTimeline(String userName, int postCount) {
        return this.getLatestTimeline(userName, postCount, 0);
    }
    default List<PostDto> getLatestTimeline(int postCount, int skipCount) {
        return this.getLatestTimeline(null, postCount, skipCount);
    }
    default List<PostDto> getLatestTimeline(int postCount) {
        return this.getLatestTimeline(null, postCount, 0);
    }
}
