package de.hska.twitterklon.redis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.PostConstruct;

import de.hska.twitterklon.api.transferobjects.PostDto;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Primary
@Profile("DEV")
public class RedisDataServiceStub implements RedisDataService {

    private Map<String, String> sessions;

    @PostConstruct
    public void postConstruct() {
        sessions = new HashMap<>();
    }

    @Override
    public void createUser(String userName, String password) {

    }

    @Override
    public Optional<String> createSession(String userName, String password, int sessionDuration) {
        String uuid = UUID.randomUUID().toString();
        sessions.put(userName, uuid);
        return Optional.of(uuid);
    }

    @Override
    public Optional<String> getUserNameFromSession(String sessionUUID) {
        return sessions.entrySet()
                .stream()
                .filter(entry -> Objects.equals(entry.getValue(), sessionUUID))
                .map(Map.Entry::getKey)
                .findAny();
    }

    @Override
    public void removeSession(String sessionUUID) {
        sessions.remove(sessionUUID);
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
    public List<PostDto> getLastPosts(String userName, int postCount, int skipCount) {
        return null;
    }

    @Override
    public List<PostDto> getLatestTimeline(String userName, int postCount, int skipCount) {
        return null;
    }
}



