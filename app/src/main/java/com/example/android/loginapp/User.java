package com.example.android.loginapp;


/**
 * Created by SoOrAj on 02-02-2018.
 */

public class User {

    private String Displayname;
    private String Email;
    private long createdAt;

    User(String displayName, String email, long createdAt) {

        this.Displayname = displayName;
        this.Email = email;
        this.createdAt = createdAt;
    }


    public String getDisplayname() {
        return Displayname;
    }


    public String getEmail() {
        return Email;
    }


    public long getCreatedAt() {
        return createdAt;
    }


}
