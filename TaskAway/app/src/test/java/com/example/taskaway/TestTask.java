package com.example.taskaway;

import java.util.ArrayList;

import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

/**
 * Created by PunamWoosaree on 2018-02-21.
 */


/**
 * Tests all methods contained in the Task class
 */
public class TestTask extends ActivityInstrumentationTestCase2{
    public TestTask() {super(Task.class);}

    /**
     * Test the constructor
     */
    @Test
    public void testTask(){
        ArrayList<Bid> bidsList = new ArrayList<>();
        ArrayList<String> picturesList = new ArrayList<>();
        Float lowestBid = 15.00f;

        Task task = new Task("name", "I'm a task", "requested", "Edmonton", bidsList, picturesList, lowestBid, "new_id");


        assertEquals(task.getName(), "name");
        assertEquals(task.getDescription(), "I'm a task");
        assertEquals(task.getStatus(), "requested");
        assertEquals(task.getLocation(), "Edmonton");
        assertEquals(task.getLowestBid(), 15.00);
        assertEquals(task.getBids(), bidsList);
        assertEquals(task.getPictures(), picturesList);
        assertEquals(task.getId(), "new_id");

    }

    /**
     * Test getName()
     */
    @Test
    public void testGetName(){
        ArrayList<Bid> bidsList = new ArrayList<>();
        ArrayList<String> picturesList = new ArrayList<>();
        Float lowestBid = 15.00f;

        Task task = new Task("name", "I'm a task", "requested", "Edmonton", bidsList, picturesList, lowestBid, null);
        assertEquals(task.getName(), "name");
    }

    /**
     * Test getDescription()
     */
    @Test
    public void testGetDescription(){
        ArrayList<Bid> bidsList = new ArrayList<>();
        ArrayList<String> picturesList = new ArrayList<>();
        Float lowestBid = 15.00f;

        Task task = new Task("name", "I'm a task", "requested", "Edmonton", bidsList, picturesList, lowestBid, null);
        assertEquals(task.getDescription(), "I'm a task");
    }

    /**
     * Test getStatus()
     */
    @Test
    public void testGetStatus(){
        ArrayList<Bid> bidsList = new ArrayList<>();
        ArrayList<String> picturesList = new ArrayList<>();
        Float lowestBid = 15.00f;

        Task task = new Task("name", "I'm a task", "requested", "Edmonton", bidsList, picturesList, lowestBid, null);
        assertEquals(task.getStatus(), "requested");
    }

    /**
     * Test getLocation()
     */
    @Test
    public void testGetLocation(){
        ArrayList<Bid> bidsList = new ArrayList<>();
        ArrayList<String> picturesList = new ArrayList<>();
        Float lowestBid = 15.00f;

        Task task = new Task("name", "I'm a task", "requested", "Edmonton", bidsList, picturesList, lowestBid, null);
        assertEquals(task.getLocation(), "Edmonton");
    }

    /**
     * Test getLowestBid()
     */
    @Test
    public void testGetLowestBid(){
        ArrayList<Bid> bidsList = new ArrayList<>();
        ArrayList<String> picturesList = new ArrayList<>();
        Float lowestBid = 15.00f;

        Task task = new Task("name", "I'm a task", "requested", "Edmonton", bidsList, picturesList, lowestBid, null);
        assertEquals(task.getLowestBid(), 15.00);
    }

    /**
     * Test getBids()
     */
    @Test
    public void testGetBids(){
        ArrayList<Bid> bidsList = new ArrayList<>();
        ArrayList<String> picturesList = new ArrayList<>();
        Float lowestBid = 15.00f;

        Task task = new Task("name", "I'm a task", "requested", "Edmonton", bidsList, picturesList, lowestBid, null);
        assertEquals(task.getBids(), bidsList);
    }

    /**
     * Test getPictures()
     */
    @Test
    public void testGetPictures(){
        ArrayList<Bid> bidsList = new ArrayList<>();
        ArrayList<String> picturesList = new ArrayList<>();
        Float lowestBid = 15.00f;

        Task task = new Task("name", "I'm a task", "requested", "Edmonton", bidsList, picturesList, lowestBid, null);
        assertEquals(task.getPictures(), picturesList);
    }

    /**
     * Test setName()
     */
    @Test
    public void testSetName(){
        ArrayList<Bid> bidsList = new ArrayList<>();
        ArrayList<String> picturesList = new ArrayList<>();
        Float lowestBid = 15.00f;

        Task task = new Task("name", "I'm a task", "requested", "Edmonton", bidsList, picturesList, lowestBid, null);
        String newName = "new name";
        try {
            task.setName(newName);
        }catch(Exception e){}

        assertEquals(task.getName(), "new name");
    }

    /**
     * Test setDescription()
     */
    @Test
    public void testSetDescription(){
        ArrayList<Bid> bidsList = new ArrayList<>();
        ArrayList<String> picturesList = new ArrayList<>();
        Float lowestBid = 15.00f;

        Task task = new Task("name", "I'm a task", "requested", "Edmonton", bidsList, picturesList, lowestBid, null);
        String newDescription = "I'm a description";
        try {
            task.setDescription(newDescription);
        }catch(Exception e){}

        assertEquals(task.getDescription(), "I'm a description");
    }

    /**
     * Test setStatus()
     */
    @Test
    public void testSetStatus(){
        ArrayList<Bid> bidsList = new ArrayList<>();
        ArrayList<String> picturesList = new ArrayList<>();
        Float lowestBid = 15.00f;

        Task task = new Task("name", "I'm a task", "requested", "Edmonton", bidsList, picturesList, lowestBid, null);
        String newStatus= "bidded";
        task.setStatus(newStatus);

        assertEquals(task.getStatus(), "bidded");
    }

    /**
     * Test setLocation()
     */
    @Test
    public void testSetLocation(){
        ArrayList<Bid> bidsList = new ArrayList<>();
        ArrayList<String> picturesList = new ArrayList<>();
        Float lowestBid = 15.00f;

        Task task = new Task("name", "I'm a task", "requested", "Edmonton", bidsList, picturesList, lowestBid, null);
        String newLocation = "Calgary";
        task.setLocation(newLocation);
        assertEquals(task.getLocation(), "Calgary");
    }

    /**
     * Test setLowestBid()
     */
    @Test
    public void testSetLowestBid(){
        ArrayList<Bid> bidsList = new ArrayList<>();
        ArrayList<String> picturesList = new ArrayList<>();
        Float lowestBid = 15.00f;

        Task task = new Task("name", "I'm a task", "requested", "Edmonton", bidsList, picturesList, lowestBid, null);
        Float newLowestBid = 7.00f;
        task.setLowestBid(newLowestBid);
        assertEquals(task.getLowestBid(), 7.00);
    }

    /**
     * Test setBids()
     */
    @Test
    public void testSetBids(){
        ArrayList<Bid> bidsList = new ArrayList<>();
        ArrayList<String> picturesList = new ArrayList<>();
        Float lowestBid = 15.00f;

        Task task = new Task("name", "I'm a task", "requested", "Edmonton", bidsList, picturesList, lowestBid, null);
        ArrayList<Bid> newBidsList = new ArrayList<>();
        task.setBids(newBidsList);
        assertEquals(task.getBids(), newBidsList);
    }

    /**
     * Test setPictures()
     */
    @Test
    public void testSetPictures(){
        ArrayList<Bid> bidsList = new ArrayList<>();
        ArrayList<String> picturesList = new ArrayList<>();
        Float lowestBid = 15.00f;

        Task task = new Task("name", "I'm a task", "requested", "Edmonton", bidsList, picturesList, lowestBid, null);
        ArrayList<String> newPicturesList = new ArrayList<>();
        task.setPictures(newPicturesList);
        assertEquals(task.getPictures(), newPicturesList);
    }

    /**
     * Test setId()
     * @author Punam Woosaree
     */
    @Test
    public void testSetId(){
        ArrayList<Bid> bidsList = new ArrayList<>();
        ArrayList<String> picturesList = new ArrayList<>();
        Float lowestBid = 15.00f;

        Task task = new Task("name", "I'm a task", "requested", "Edmonton", bidsList, picturesList, lowestBid, null);
        task.setId("betterID");
        assertEquals(task.getId(), "betterID");
    }

    /**
     * Test setId()
     * @author Punam Woosaree
     */
    @Test
    public void testGetId(){
        ArrayList<Bid> bidsList = new ArrayList<>();
        ArrayList<String> picturesList = new ArrayList<>();
        Float lowestBid = 15.00f;

        Task task = new Task("name", "I'm a task", "requested", "Edmonton", bidsList, picturesList, lowestBid, "coolId");
        assertEquals(task.getId(), "coolId");
    }

    /**
     * Test getLatitude()
     * @author Diane Boytang
     */
    @Test
    public void testGetLatitude(){
        String locationString = "59 Main Boulevard, Sherwood Park, AB, Canada";
        Task task = new Task("name", "I'm a task", "requested", locationString, null, null, null, "coolId");

        Double latitudeCorrect = 53.5374022;

        Double latitudeTest = task.getLatitude();
        assertEquals(latitudeCorrect, latitudeTest);
    }

    /**
     * Test getLongitude()
     * @author Diane Boytang
     */
    @Test
    public void testGetLongitude(){
        String locationString = "59 Main Boulevard, Sherwood Park, AB, Canada";
        Task task = new Task("name", "I'm a task", "requested", locationString, null, null, null, "coolId");

        Double longitudeCorrect = -113.3087432;
        Double longitudeTest = task.getLongitude();

        assertEquals(longitudeCorrect, longitudeTest);
    }
}
