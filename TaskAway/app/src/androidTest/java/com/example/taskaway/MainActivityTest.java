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
        solo.clickOnView(solo.getView(R.drawable.ic_menu));
        solo.clickOnMenuItem("Profile");
        solo.assertCurrentActivity("Wrong Activity", ViewProfile.class);
        solo.goBack();

        //Test if activity goes to logout page
        solo.clickOnView(solo.getView(R.drawable.ic_menu));
        solo.clickOnMenuItem("Logout");
        solo.assertCurrentActivity("Wrong Activity", Login.class);

    }

    /**
     * Used to test switching to fragments
     */
    public void testFragments(){
        MainActivity activity = (MainActivity) solo.getCurrentActivity();

        //Swtich to My Bids screen
        solo.clickInRecyclerView(1);
        solo.assertCurrentActivity("Wrong Activity", MyBids.class);

        //Switch to My Assigned Jobs screen
        solo.clickInRecyclerView(2);
        solo.assertCurrentActivity("Wrong Activity", MyAssigned.class);

        //Switch to All Bids screen
        solo.clickInRecyclerView(3);
        solo.assertCurrentActivity("Wrong Activity", AllBids.class);

        //Switch to My Jobs screen
        solo.clickInRecyclerView(0);
        solo.assertCurrentActivity("Wrong Activity", MyJobs.class);
        
    }

    /**
     * Tests if the Add Task Activity appears when clicked
     */
    public void testAddActivity(){
        MainActivity activity = (MainActivity) solo.getCurrentActivity();
        solo.clickOnView(solo.getView(R.id.add_id));
        solo.assertCurrentActivity("Wrong Activity", AddTaskActivity.class);

    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }


}
