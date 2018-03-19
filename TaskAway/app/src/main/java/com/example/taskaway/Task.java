package com.example.taskaway;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import 	android.util.Base64;

/**
 * Created by sameerah on 18/02/18.
 */

/**
 * Represents a task.
 */
public class Task implements Serializable { // made Serializable to that Task can be passed in intents
    private String name;
    private String description;
    private ArrayList<Bid> bids;
    private String status;
    private Float lowestBid;
    private String location;
    private ArrayList<String> pictures;
    private String id;
    private String creatorId;
    private boolean deleted = false;
    // TODO: getUserName -> for use when user is offline or server unavailable

    /**
     * Constructor of task.
     * @param name - name of task
     * @param description - description of task
     * @param status - current status of task (assigned, requested, done)
     * @param location - location of task
     * @param bids - bids currently made on task
     * @param pictures - pictures related to task
     * @param lowestBid - lowest bid of all other bids made on task
     */
    Task(String name, String description, String status, String location, ArrayList<Bid> bids, ArrayList<String> pictures, Float lowestBid, String id){
        this.name = name;
        this.description = description;
        this.status = status;
        this.location = location;
        this.lowestBid = lowestBid;
        this.bids = bids;
        this.pictures = pictures;
        this.id = id;

    }

    /**
     * Returns name of the task.
     * @return - name of task
     */
    public String getName(){
        return name;
    }

    /**
     * Returns id of the task.
     * @return - id of task
     */
    public String getId(){
        return id;
    }

    /**
     * Returns creatorId of task - The Id of the User who created it
     * @return - creatorId of the task
     */
    public String getCreatorId(){
        return creatorId;
    }

    /**
     * Returns description of task.
     * @return - description of task
     */
    public String getDescription(){
        return description;
    }

    /**
     * Returns current status of task.
     * @return - status of task
     */
    public String getStatus(){
        return status;
    }

    /**
     * Returns location of task.
     * @return - location of task
     */
    public String getLocation() {
        return location;
    }

    /**
     * Returns lowest bid (of all bids) of task
     * @return - lowest bid for task
     */
    public Float getLowestBid(){
        return lowestBid;
    }

    /**
     * Returns pictures related to task.
     * @return - pictures of task
     */
    public ArrayList<String> getPictures(){
        return pictures;
    }

    /**
     * Returns all bids made on task.
     * @return - arraylist of bid objects relating to task
     */
    public ArrayList<Bid> getBids(){
        return bids;
    }

    /**
     * Sets name of task.
     * @param name - name to be set for task.
     */
    public void setName (String name){
        this.name = name;
    }

    /**
     * Sets id of task.
     * @param id - id to be set for task
     */
    public void setId(String id){
        this.id = id;
    }

    /**
     * Sets creatorId of task - The Id of the User who created it
     * @param id - creatorId to be set for task
     */
    public void setCreatorId(String id){
        this.creatorId = id;
    }

    /**
     * Sets description of task.
     * @param description - description to be set for task
     */
    public void setDescription(String description){
        this.description = description;
    }


    /**
     * Sets bids for task.
     * @param bids - arraylist of bid objects to be set for task
     */
    public void setBids(ArrayList<Bid> bids){
        this.bids = bids;
    }

    /**
     * Sets the lowest bid (of all bids) for task.
     * @param lowestBid - lowest bid (of all bids) to be set for task
     */
    public void setLowestBid(Float lowestBid){
        this.lowestBid = lowestBid;
    }

    /**
     * Sets a status for task.
     * @param status - status to be set for task
     */
    public void setStatus(String status){
        this.status = status;
    }

    /**
     * Sets location for task.
     * @param location - location to be set for task
     */
    public void setLocation(String location){
        this.location = location;
    }

    /**
     * Sets pictures for task.
     * @param pictures - pictures to be set for task
     */
    public void setPictures(ArrayList<String> pictures){
        this.pictures = pictures;
    }

    /**
     * Adds a picture to the task.
     * @param picture - pictures to be set for task
     */
    public void addPicture(String picture){
        this.pictures.add(picture);
    }

    /**
     * Sets the deleted boolean value to true, signifying this has been deleted from the server
     */
    public void markDeleted() {
        this.deleted = true;
    }

    /**
     * Returns whether the task has been deleted from the server
     * @returns deleted boolean variable
     */
    public boolean isDeleted() {
        return this.deleted;
    }

    /**
     * Sorts the ArrayList of bids
     * @return lowest bid
     */
    public Bid findLowestBid() {
        Collections.sort(bids);
        Bid bid = bids.get(0);
        return bid;

    }



    /**
     * Represents attributes and information of a task as a string
     * @return - attributes of a task as a string
     */
    @Override
    public String toString(){
        // TODO: 18/02/18
        return "";
    }
}
