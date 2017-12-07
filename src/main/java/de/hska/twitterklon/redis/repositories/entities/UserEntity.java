package de.hska.twitterklon.redis.repositories.entities;

import com.sun.istack.internal.Nullable;
import groovy.transform.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@EqualsAndHashCode
@RedisHash("Users")
public class UserEntity {
    @Id
    private String userName;
    private String password;
    @Nullable
    private String authKey;

    public UserEntity(String userName, String password, @Nullable String authKey) {
        this.userName = userName;
        this.password = password;
        this.authKey = authKey;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }
}
