package com.example.user.cryptoinfo;

/**
 * Created by User on 29/03/2018.
 */

public class userInfo {
    String userInfoName;
    String userInfoEmail;

    public userInfo() {
    }

    public userInfo(String userInfoName, String userInfoEmail) {
        this.userInfoName = userInfoName;
        this.userInfoEmail = userInfoEmail;
    }

    public String getUserInfoName() {
        return userInfoName;
    }

    public void setUserInfoName(String userInfoName) {
        this.userInfoName = userInfoName;
    }

    public String getUserInfoEmail() {
        return userInfoEmail;
    }

    public void setUserInfoEmail(String userInfoEmail) {
        this.userInfoEmail = userInfoEmail;
    }
}
