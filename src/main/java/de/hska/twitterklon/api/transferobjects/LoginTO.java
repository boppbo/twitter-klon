package de.hska.twitterklon.api.transferobjects;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.hska.twitterklon.api.transferobjects.validators.ValidUserName;

public class LoginTO {

    @ValidUserName
    @JsonProperty("uid")
    private String userId;

    @NotNull(message = "password.not.null")
    @JsonProperty("pw")
    private String password;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginTO{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
