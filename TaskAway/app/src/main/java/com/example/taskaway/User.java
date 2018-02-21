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
    private TaskList reqTasks;
    private TaskList bidTasks; //updated var name to be more descriptive - 20/02/18
    private TaskList assignedTasks; //updated var name to be more descriptive - 20/02/18
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
        this.reqTasks = new TaskList();
        this.bidTasks = new TaskList();
        this.assignedTasks = new TaskList();
    }

    /**
     * Alternate constructor of user which takes in reqTasks, bidTasks and assignedTasks as parameters
     * @param username - username of user
     * @param email - email of user
     * @param phone - phone number of user
     * @param password - password of user
     * @param reqTasks - list of tasks requested by the user
     * @param bidTasks - list of tasks bid on by the user
     * @param assignedTasks - lists of tasks assigned to the user
     */
    User(String username, String email, String phone, String password, TaskList reqTasks, TaskList bidTasks, TaskList assignedTasks){
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
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


    // TODO: public void setID, public int getID - 18/02/18
    //public int getID(){ return ID;}
    //public void setID(int ID){ this.ID = ID; }
}
