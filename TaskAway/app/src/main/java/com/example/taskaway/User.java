package com.example.taskaway;


/**
 * Created by sameerah on 18/02/18.
 */

/**
 * Represents a user.
 */
public class User {
    private String username;
    private String email;
    private String phone;
    private TaskList reqTask;
    private TaskList proTask;
    private TaskList otherTask;
    private String password;
    // TODO: private int id; 18/02/18
    //private int ID;

    /**
     * Constructor of user.
     * @param username - username of user
     * @param email - email of user
     * @param phone - phone number of user
     * @param password - password of user (as a String as of 18/02/18)
     */
    User(String username, String email, String phone, String password){
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    /**
     * Returns username of user.
     * @return - username of user
     */
    public String getUsername(){
        return username;
    }

    /**
     * Returns email of user.
     * @return - email of user
     */
    public String getEmail(){
        return email;
    }

    /**
     * Returns of phone number of user.
     * @return - phone number of user.
     */
    public String getPhone(){
        return phone;
    }

    /**
     * Returns password of user.
     * @return - password of user
     */
    public String getPassword(){
        return password;
    }

    /**
     * Sets username for user.
     * @param username - username to be set
     */
    public void setUsername(String username){
        this.username = username;
    }

    /**
     * Sets email for user.
     * @param email - email to be set
     */
    public void setEmail(String email){
        this.email = email;
    }

    /**
     * Sets phone number for user.
     * @param phone - phone number to be set
     */
    public void setPhone(String phone){
        this.phone = phone;
    }

    /**
     * Sets password for user.
     * @param password - password (String as of 18/02/18) to be set
     */
    public void setPassword(String password){
        this.password = password;
    }



    // TODO: public void setID, public int getID - 18/02/18
    //public int getID(){ return ID;}
    //public void setID(int ID){ this.ID = ID; }
}
