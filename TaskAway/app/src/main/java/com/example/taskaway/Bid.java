package com.example.taskaway;

/**
 * Created by sameerah on 18/02/18.
 */

/**
 * Represents a user's bid on a task.
 */
public class Bid {
    private Float amount;
    private String userId;

    /**
     * Constructor of bid.
     * @param amount - the amount of a bid
     */
    Bid(Float amount){
        this.amount = amount;
    }
    Bid(String userId, Float amount){
        this.userId = userId;
        this.amount = amount;
    }
    Bid(){}

    Bid(User user, Float amount){
        this.userId = user.getId();
        this.amount = amount;
    }

    /**
     * Returns the amount of a bid.
     * @return - current amount of bid
     */
    public Float getAmount(){
        return amount;
    }

    /**
     * Sets a bid to a specified amount.
     * @param amount - amount to be set for bid
     */
    public void setAmount(Float amount){
        this.amount = amount;
    }

    /**
     * Returns the id of the user who placed the bid.
     * @return - id of user
     */
    public String getUserId(){
        return userId;
    }

    /**
     * Sets the userId associated to this bid.
     * @param id - user id to be set for bid
     */
    public void setUserId(String id){
        this.userId = id;
    }

    /**
     * Sets the userId associated to this bid.
     * @param user - user who's id will be associated with this bid.
     */
    public void setUserId(User user){
        this.userId = user.getId();
    }

    /**
     * Represents bid information as a string.
     * @return - string form of bid information
     */
    @Override
    public String toString(){
        // TODO: 18/02/18
        return "";
    }
}
