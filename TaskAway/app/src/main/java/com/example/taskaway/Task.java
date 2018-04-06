package com.example.taskaway;

import java.io.Serializable;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;

import 	android.util.Base64;
import android.util.Log;

import org.json.JSONObject;

/**
 * Represents a task.
 * @author Sameerah Wajahat
 * Created on 18/02/18
 */
public class Task implements Serializable { // made Serializable to that Task can be passed in intents
    private String name;
    private String description;
    private ArrayList<Bid> bids = new ArrayList<>();
    private String status;
    private Float lowestBid;
    private String location;
    private ArrayList<String> pictures;
    private String id;
    private String creatorId;
    private boolean deleted = false;
    private boolean hasNewBids = false;
    private String syncInstruction = "";

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
     * Returns latitude of the task location.
     * @return - latitude
     * @author - Diane Boytang
     */
    public double getLatitude() {
        if (this.location != null){
            try{
                String task_location_encoded = URLEncoder.encode(location, "UTF-8");
                String location_uri ="https://maps.googleapis.com/maps/api/geocode/json?address=" + task_location_encoded + "&key=AIzaSyBOflugbssWI1J6qUsPPt7-rEeF01MKOuY";
                GetLocationJson locationJson = new GetLocationJson();
                locationJson.execute(location_uri);

                JSONObject jsonObject = new JSONObject(locationJson.get());
                JSONObject locationGeo = jsonObject.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
                String lat = locationGeo.getString("lat");

                Double latitude = Double.parseDouble(lat);
                return latitude;
            } catch (Exception e){
                Log.i("TASK", "COULD NOT GET LATITUDE");
                e.printStackTrace();
            }
        }
        return -1;
    }

    /**
     * Returns longitude of the task location.
     * @return - longitude
     * @author - Diane Boytang
     */
    public double getLongitude() {
        if (this.location != null){
            try{
                String task_location_encoded = URLEncoder.encode(location, "UTF-8");
                String location_uri ="https://maps.googleapis.com/maps/api/geocode/json?address=" + task_location_encoded + "&key=AIzaSyBOflugbssWI1J6qUsPPt7-rEeF01MKOuY";
                GetLocationJson locationJson = new GetLocationJson();
                locationJson.execute(location_uri);

                JSONObject jsonObject = new JSONObject(locationJson.get());
                JSONObject locationGeo = jsonObject.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
                String lng = locationGeo.getString("lng");

                Double longitude = Double.parseDouble(lng);
                return longitude;
            } catch (Exception e){
                Log.i("TASK", "COULD NOT GET LONGITUDE");
                e.printStackTrace();
            }
        }
        return -1;
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
        return this.bids;
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
     * Sets whether there are unread bids on the task
     */
    public void setHasNewBids(boolean bool){
        this.hasNewBids = bool;
    }

    /**
     * Returns whether the task has been deleted from the server
     * @returns deleted boolean variable
     */
    public boolean isDeleted() {
        return this.deleted;
    }

    /**
     * Returns whether the task has bids unseen by the creator
     * @return hasUnreadBids
     */
    public boolean hasNewBids() {
        return this.hasNewBids;
    }

    /**
     * Sorts the ArrayList of bids - used for finding the lowest bid
     * @return lowest bid
     * @author Diane Boytang
     */
    public Bid findLowestBid() {
        Collections.sort(bids);
        Bid bid = null;
        if (bids.size() > 0){
            bid = bids.get(0);
        }
        return bid;
    }

    /**
     * Deletes the bid placed by the user with user id provided, if it exists
     * @param userid - the ID of the user who's bid is to be deleted
     * @author Adrian Schuldhaus
     */
    public void deleteBid(String userid) {
        for (Bid b : bids){
            if (b.getUserId().equals(userid)){
                bids.remove(b);
                return;
            }
        }
    }

    /**
     * Gets the operation the task should complete in order to sync server on next login.
     * @return the instruction String
     */
    public String getSyncInstruction() {
        return syncInstruction;
    }

    /**
     * Sets the operation the task should complete in order to sync server on next login.
     * @param instruction - the instruction String to assign it to
     */
    public void setSyncInstruction(String instruction) {
        this.syncInstruction = instruction;
    }

    /**
     * Clears the sync instruction field. Intended for after a sync has been done.
     */
    public void clearSyncInstruction() {
        this.syncInstruction = "";
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
