package com.example.taskaway;

/*
 * Copyright (c) 2018 Team 19, CMPUT301. University of Alberta - All rights reserved.
 * You may use distribute and modify this code under terms and conditions of Code of Student Behavior at
 * University of Alberta
 * You can find a copy of this license in this project.
 *
 * @author Punam Woosaree April 6th 2018
 *
 * Ensures that main activity receives and sends out the correct information
 */


import android.test.ActivityInstrumentationTestCase2;
import android.app.Activity;
import android.widget.EditText;
import com.example.taskaway.MainActivity;
import com.robotium.solo.Solo;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private Solo solo;

    public MainActivityTest() {
        super(com.example.taskaway.MainActivity.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }


    public void testStart() throws Exception{
        Activity activity = getActivity();
    }

    /**
     * Used to test the intents and actions of the Toolbar
     */
    public void testToolbar(){

        MainActivity activity = (MainActivity) solo.getCurrentActivity();

        //Test if activity goes to profile page
        solo.clickOnView(solo.getView(R.id.toolbar));
        solo.clickOnMenuItem("Profile");
        solo.assertCurrentActivity("Profile", ViewProfile.class);
        solo.goBack();

        //Test if activity goes to logout page
        solo.clickOnView(solo.getView(R.id.toolbar));
        solo.clickOnMenuItem("Logout");
        solo.assertCurrentActivity("Login screen", Login.class);

    }

    /**
     * Used to test switching to fragments
     */
    public void testFragments(){
        MainActivity activity = (MainActivity) solo.getCurrentActivity();

        //Swtich to My Bids screen
        solo.clickOnView(solo.getView(R.id.viewpager_id, 1));
        solo.assertCurrentActivity("My Bids", MyBids.class);

        //Switch to My Assigned Jobs screen
        solo.clickOnView(solo.getView(R.id.viewpager_id, 2));
        solo.assertCurrentActivity("My Assigned Jobs", MyAssigned.class);

        //Switch to All Bids screen
        solo.clickOnView(solo.getView(R.id.viewpager_id, 3));
        solo.assertCurrentActivity("My Bids", AllBids.class);

        //Switch to My Jobs screen
        solo.clickOnView(solo.getView(R.id.viewpager_id, 0));
        solo.assertCurrentActivity("My Jobs", MyJobs.class);
        
    }

    /**
     * Tests if the Add Task Activity appears when clicked
     */
    public void testAddActivity(){
        MainActivity activity = (MainActivity) solo.getCurrentActivity();
        solo.clickOnView(solo.getView(R.id.add_id));
        solo.assertCurrentActivity("Add Task", AddTaskActivity.class);

    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }


}
