package com.example.hwang.myapplication.dto;

/**
 * Created by hwang on 2018-02-03.
 */

//회원
public class User {
    int id;          //회원Idx;
    String userId;       //회원ID
    String userPassword; //회원비밀번호
    String userName;     //회원성명

    public User(int id, String userId, String userPassword, String userName) {
        this.id = id;
        this.userId = userId;
        this.userPassword = userPassword;
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
