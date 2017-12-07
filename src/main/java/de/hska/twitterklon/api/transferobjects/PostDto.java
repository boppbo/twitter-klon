package de.hska.twitterklon.api.transferobjects;

import groovy.transform.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@EqualsAndHashCode
@RedisHash("Posts")
public class PostDto {
    @Id
    private String id;
    private String content;
    private String creationTime;
    private String userName;

    public PostDto() {
    }

    public PostDto(String content, String userName) {
        this("", content, userName, String.valueOf(System.currentTimeMillis()));
    }

    public PostDto(String id, String content, String userName, String creationTime) {
        this.id = id;
        this.content = content;
        this.creationTime = creationTime;
        this.userName = userName;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getUserName() { return userName; }

    public void setUserName(String userName) { this.userName = userName; }
}
