package com.example.taskaway;

import java.util.ArrayList;

/**
 * Created by sameerah on 18/02/18.
 */

/**
 * Represents a task.
 */
public class Task {
    private String name;
    private String description;
    private ArrayList<Bid> bids;
    private String status;
    private Float lowestBid;
    private String location;
    private ArrayList<Task> pictures;
    private String id;

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
    Task(String name, String description, String status, String location, ArrayList<Bid> bids, ArrayList<Task> pictures, Float lowestBid){
        this.name = name;
        this.description = description;
        this.status = status;
        this.location = location;
        this.lowestBid = lowestBid;
        this.bids = bids;
        this.pictures = pictures;
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
    public ArrayList<Task> getPictures(){
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
     * Throws TaskNameTooLongException if the name is greater than 30 chars
     */
    public void setName (String name) throws TaskNameTooLongException{
        if(name.length() > 30){
            throw new TaskNameTooLongException();
        }
        else{
            this.name = name;
        }
    }

    /**
     * Sets id of task.
     * @param id - id to be set for task
     */
    public void setId(String id){
        this.id = id;
    }

    /**
     * Sets description of task.
     * @param description - description to be set for task
     * Throws TaskDescriptionTooLongException if the task description is greater than 300 chars
     */
    public void setDescription(String description) throws TaskDescriptionTooLongException{
        if(description.length() > 300){
            throw new TaskDescriptionTooLongException();
        }
        else {
            this.description = description;
        }
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
    public void setPictures(ArrayList<Task> pictures){
        this.pictures = pictures;
    }

    // TODO public void setID, getID - 18/02/18

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
