package de.hska.twitterklon.redis;

import de.hska.twitterklon.api.transferobjects.PostDto;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
@Profile("DEV")
public class RedisDataServiceStub implements RedisDataService {
    @Override
    public Optional<String> createSession(String userName, String password, int sessionDuration) {
        return null;
    }

    @Override
    public Optional<String> getUserNameFromSession(String sessionUUID) {
        return null;
    }

    @Override
    public void addFollower(String follower, String following) {

    }

    @Override
    public void removeFollower(String follower, String following) {

    }

    @Override
    public List<String> getFollower(String UserName) {
        return null;
    }

    @Override
    public List<String> getFollowing(String UserName) {
        return null;
    }

    @Override
    public List<PostDto> getLastPosts(String UserName, int postCount) {
        return null;
    }

    @Override
    public List<PostDto> getLatestTimeline(int postCount) {
        return null;
    }

    @Override
    public List<PostDto> getLatestTimeline(String Username, int postCount) {
        return null;
    }
}



