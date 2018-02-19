package com.example.taskaway;

/**
 * Created by sameerah on 18/02/18.
 */

public class Bid {
    private Float amount;

    Bid(Float amount){
        this.amount = amount;
    }

    public Float getAmount(){
        return amount;
    }

    public void setAmount(Float amount){
        this.amount = amount;
    }

    @Override
    public String toString(){
        // TODO: 18/02/18
        return "";
    }
}
