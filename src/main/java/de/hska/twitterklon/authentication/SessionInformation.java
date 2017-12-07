package de.hska.twitterklon.authentication;

import org.springframework.core.NamedThreadLocal;

public abstract class SessionInformation {

    private static final ThreadLocal<UserInfo> user = new NamedThreadLocal<>("UserInformation");

    public static String getUserName() {
      return user.get() == null ? "" : user.get().name;
    }


    public static String getUuid() {
        return user.get() == null ? "" : user.get().uuid;
    }

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

    public static boolean isUserSignedIn() {
        return user.get() != null && user.get().name != null && user.get().uuid != null;
    }
}