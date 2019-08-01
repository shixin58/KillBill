package com.bride.demon.model;

/**
 * <p>Created by shixin on 2019-08-01.
 */
public class User {

    public String userId;
    public String name;
    public String portraitUri;

    public User(String userId, String name, String portraitUri) {
        this.userId = userId;
        this.name = name;
        this.portraitUri = portraitUri;
    }
}
