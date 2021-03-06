package de.hska.twitterklon.api.transferobjects;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.hska.twitterklon.api.transferobjects.validators.ValidContent;
import de.hska.twitterklon.authentication.SessionInformation;
import groovy.transform.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode
@RedisHash("Posts")
public class PostDto {
    @Id
    private String id;

    @NotNull(message = "content.not.null")
    @ValidContent
    private String content;

    @JsonProperty("time")
    private String creationTime;

    @JsonProperty("name")
    private String userName;

    public PostDto() {
    }

    public PostDto(String content) {
        this(null, content, SessionInformation.getUserName(), String.valueOf(System.currentTimeMillis()));
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

    public String getUserName() {
        return userName;
    }

    private void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "PostDto{" +
                "content='" + content + '\'' +
                ", creationTime=" + creationTime +
                ", userName='" + userName + '\'' +
                '}';
    }
}
