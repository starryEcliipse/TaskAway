package com.example.taskaway;

import java.security.MessageDigest;

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
    private TaskList reqTasks;
    private TaskList bidTasks; //updated var name to be more descriptive - 20/02/18
    private TaskList assignedTasks; //updated var name to be more descriptive - 20/02/18
    private String passwordHash;
    private String id;
    private boolean deleted = false;

    /**
     * Constructor of user.
     * @param username - username of user
     * @param email - email of user
     * @param phone - phone number of user
     */
    User(String username, String email, String phone){
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.reqTasks = new TaskList();
        this.bidTasks = new TaskList();
        this.assignedTasks = new TaskList();
    }

    /**
     * Alternate constructor of user which takes in reqTasks, bidTasks and assignedTasks as parameters
     * @param username - username of user
     * @param email - email of user
     * @param phone - phone number of user
     * @param passwordHash - password hash of user
     * @param reqTasks - list of tasks requested by the user
     * @param bidTasks - list of tasks bid on by the user
     * @param assignedTasks - lists of tasks assigned to the user
     */
    User(String username, String email, String phone, String passwordHash, TaskList reqTasks, TaskList bidTasks, TaskList assignedTasks){
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.passwordHash = passwordHash;
        this.reqTasks = reqTasks;
        this.bidTasks = bidTasks;
        this.assignedTasks = assignedTasks;
    }

    /**
     * Returns username of user.
     * @return - username of user
     */
    public String getUsername(){
        return username;
    }

    /**
     * Returns id of user.
     * @return - id of user
     */
    public String getId(){
        return id;
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
     * Returns the list of requested tasks for the user (tasks the user has posted)
     * @return list of requested tasks of the user
     */
    public TaskList getReqTasks() { return reqTasks; }

    /**
     * Returns the list of provided tasks for the user (tasks the user has bid on)
     * @return list of provided tasks of the user
     */
    public TaskList getBidTasks() { return bidTasks; }

    /**
     * Returns the list of tasks the user has been assigned
     * @return list of assigned tasks of the user has been assigned to do
     */
    public TaskList getAssignedTasks() { return assignedTasks; }

    /**
     * Sets username for user.
     * @param username - username to be set
     */
    public void setUsername(String username){
        this.username = username;
    }

    /**
     * Sets id for user.
     * @param id - id to be set
     */
    public void setId(String id){
        this.id = id;
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
     * Sets the list of tasks the user requests
     * @param reqTasks - list of requested tasks
     */
    public void setReqTasks (TaskList reqTasks) { this.reqTasks = reqTasks; }

    /**
     * Sets the list of tasks the user has bid on
     * @param bidTasks - list of bid on tasks
     */
    public void setBidTasks (TaskList bidTasks) { this.bidTasks = bidTasks; }

    /**
     * Sets the list of tasks the user has been assigned
     * @param assignedTasks - list of assigned tasks
     */
    public void setAssignedTasks (TaskList assignedTasks) { this.assignedTasks = assignedTasks; }

    /**
     * Sets the deleted boolean value to true, signifying this has been deleted from the server
     */
    public void markDeleted() {
        this.deleted = true;
    }

    public void setPasswordHash(String hash) {
        this.passwordHash = hash;
    }

    public String getPasswordHash() {
        return this.passwordHash;
    }

    public void setPassword(String password) {
        this.passwordHash = hash_SHA256(password);
    }

    public String hash_SHA256(String str) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(str.getBytes());
            return new String(hash);
        }catch(Exception e){}
        return "SHA256 HASH ERROR";
    }

    public boolean verifyPassword(String comparisonPassword){
        return this.passwordHash.equals(hash_SHA256(comparisonPassword));
    }


}
