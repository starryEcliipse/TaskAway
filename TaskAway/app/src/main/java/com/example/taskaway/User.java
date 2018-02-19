package com.example.taskaway;

import java.util.ArrayList;

/**
 * Created by sameerah on 18/02/18.
 */

public class User {
    private String username;
    private String email;
    private String phone;
    private ArrayList<Task> reqTask;
    private ArrayList<Task> proTask;
    private ArrayList<Task> otherTask;
    private String password;

    User(String username, String email, String phone, String password){
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }


    public String getUsername(){
        return username;
    }

    public String getEmail(){
        return email;
    }

    public String getPhone(){
        return phone;
    }

    public String getPassword(){
        return password;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public void setPassword(String password){
        this.password = password;
    }
}
