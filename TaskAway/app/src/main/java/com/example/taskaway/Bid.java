package com.example.taskaway;

/**
 * Created by sameerah on 18/02/18.
 */

/**
 * Represents a user's bid on a task.
 */
public class Bid {
    private Float amount;
    // TODO: private int userId 18/02/18

    /**
     * Constructor of bid.
     * @param amount - the amount of a bid
     */
    Bid(){}
    Bid(Float amount){
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
     * Represents bid information as a string.
     * @return - string form of bid information
     */
    @Override
    public String toString(){
        // TODO: 18/02/18
        return "";
    }
}
