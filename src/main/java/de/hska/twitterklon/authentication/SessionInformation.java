package de.hska.twitterklon.authentication;

import org.springframework.core.NamedThreadLocal;

public abstract class SessionInformation {

    private static final ThreadLocal<UserInfo> user = new NamedThreadLocal<>("UserInformation");

    private static class UserInfo {
        String name;
        String uuid;
    }
    public static void setUser(String name, String uuid) {
        UserInfo userInfo = new UserInfo();
        userInfo.name = name;
        userInfo.uuid = uuid;
        user.set(userInfo);
    }

    public static boolean isUserSignedIn(String name) {
        UserInfo userInfo = user.get();
        return userInfo != null && userInfo.name.equals(name);
    }
}