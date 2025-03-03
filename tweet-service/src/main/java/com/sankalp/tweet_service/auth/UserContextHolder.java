package com.sankalp.tweet_service.auth;

public class UserContextHolder {

    private static final ThreadLocal<Long> currentUserId = new ThreadLocal<>();
    private static final ThreadLocal<String> currentUserName = new ThreadLocal<>();

    public static Long getCurrentUserId(){
        return currentUserId.get();
    }

    public static String getCurrentUserName(){
        return currentUserName.get();
    }

    static void setCurrentUserId(Long userId){
        currentUserId.set(userId);
    }

    static void setCurrentUserName(String userName){
        currentUserName.set(userName);
    }

    static void clear(){
        currentUserId.remove();
        currentUserName.remove();
    }

}
