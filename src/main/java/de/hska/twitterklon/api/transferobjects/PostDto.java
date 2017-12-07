package de.hska.twitterklon.api.transferobjects;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.hska.twitterklon.api.transferobjects.validators.ValidContent;

public class PostDto {

    @NotNull(message = "content.not.null")
    private String content;

    @NotNull(message = "time.not.null")
    @JsonProperty("time")
    private String creationTime;

    @ValidContent
    @JsonProperty("name")
    private String userName;

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

    public void setUserName(String userName) {
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
