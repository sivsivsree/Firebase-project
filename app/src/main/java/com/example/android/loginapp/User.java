package com.example.android.loginapp;


/**
 * Created by SoOrAj on 02-02-2018.
 */

public class User {
    String Displayname;
    String Email;
    long createdAt;

    public User(String displayname, String email, long createdAt){

        this.Displayname = displayname;
        this.Email =email;
        this.createdAt=createdAt;
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
