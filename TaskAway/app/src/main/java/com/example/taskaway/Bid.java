package com.example.taskaway;

import java.io.Serializable;

/**
 * Represents a user's bid on a task.
 *
 * @author Sameerah Wajahat
 * Created on 18/02/18
 */
public class Bid implements Comparable<Bid>, Serializable { //implement comparable in order to sort the bids
    private Float amount;
    private String userId;

    /**
     * Constructor of bid.
     * @param amount - the amount of a bid
     */
    Bid(Float amount){
        this.amount = amount;
    }

    /**
     * Constructor of bid.
     * @param userId - user's id
     * @param amount - the amount of a bid
     */
    Bid(String userId, Float amount){
        this.userId = userId;
        this.amount = amount;
    }

    /**
     * Consstructor of a bid.
     * @param user - current user
     * @param amount - amoutn of a bid
     */
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
     * Compares 2 bids to one another
     * @param bid bid object to compare to
     * @return -1 if this.amount < bid.amount; +1 if this.amount >= bid.amount
     *
     * @author Diane Boytang
     */
    @Override
    public int compareTo(Bid bid) {
        if (this.getAmount()< bid.getAmount()){
            return -1;
        }else{
            return 1;
        }
    }
}
