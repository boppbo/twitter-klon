package de.hska.twitterklon.api.transferobjects;

import java.security.Timestamp;

public class PostDto {
    private String Content;
    private Timestamp CreationTime;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public Timestamp getCreationTime() {
        return CreationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        CreationTime = creationTime;
    }
}
