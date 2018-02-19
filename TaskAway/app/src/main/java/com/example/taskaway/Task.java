package com.example.taskaway;

import java.util.ArrayList;

/**
 * Created by sameerah on 18/02/18.
 */

public class Task {
    private String name;
    private String description;
    private ArrayList<Bid> bids;
    private String status;
    private Float lowestBid;
    private String location;
    private ArrayList<Task> pictures;

    Task(String name, String description, String status, String location, ArrayList<Bid> bids, ArrayList<Task> pictures, Float lowestBid){
        this.name = name;
        this.description = description;
        this.status = status;
        this.location = location;
        this.lowestBid = lowestBid;
        this.bids = bids;
        this.pictures = pictures;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public String getStatus(){
        return status;
    }

    public String getLocation() {return location; }

    public Float getLowestBid(){
        return lowestBid;
    }

    public ArrayList<Task> getPictures(){
        return pictures;
    }

    public ArrayList<Bid> bids(){
        return bids;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setBids(ArrayList<Bid> bids){
        this.bids = bids;
    }

    public void setLowestBid(Float lowestBid){
        this.lowestBid = lowestBid;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public void setPictures(ArrayList<Task> pictures){
        this.pictures = pictures;
    }

    @Override
    public String toString(){
        // TODO: 18/02/18
        return "";
    }
}
