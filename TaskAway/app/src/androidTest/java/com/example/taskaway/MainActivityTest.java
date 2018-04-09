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


import android.support.design.widget.TabLayout;
import android.test.ActivityInstrumentationTestCase2;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.example.taskaway.MainActivity;
import com.robotium.solo.Solo;

public class MainActivityTest extends ActivityInstrumentationTestCase2{

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
     * Used to test profile item in action bar
     */
    public void testToolbar1(){

        Login activity = (Login)solo.getCurrentActivity();
        solo.assertCurrentActivity("Wrong Activity", Login.class);

        //check if user already exists
        User user = ServerWrapper.getUserFromUsername("TestUser");
        if (user!=null){
            ServerWrapper.deleteUser(user);
        }

        solo.enterText((EditText) solo.getView(R.id.neweditTextUsername), "TestUser");
        solo.enterText((EditText) solo.getView(R.id.neweditTextPassword), "test");
        solo.clickOnButton("Register");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        //Test if activity goes to profile page
        solo.clickOnActionBarItem(1);
        solo.assertCurrentActivity("Wrong Activity", ViewProfile.class);
    }

    /**
     * Test logout capabilities
     */
    public void testToolbar2(){

        Login activity = (Login)solo.getCurrentActivity();
        solo.assertCurrentActivity("Wrong Activity", Login.class);

        //check if user already exists
        User user = ServerWrapper.getUserFromUsername("TestUser");
        if (user!=null){
            ServerWrapper.deleteUser(user);
        }

        solo.enterText((EditText) solo.getView(R.id.neweditTextUsername), "TestUser");
        solo.enterText((EditText) solo.getView(R.id.neweditTextPassword), "test");
        solo.clickOnButton("Register");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        //Test if activity goes to profile page
        solo.clickOnActionBarItem(2);
        solo.assertCurrentActivity("Wrong Activity", Login.class);
    }

    /**
     * Used to test switching to fragments
     */
    public void testFragments(){
        MainActivity activity = (MainActivity) solo.getCurrentActivity();

        //Switch to My Bids screen
        ViewGroup tabs = (ViewGroup) solo.getView(R.id.tablayout_id);
        View myBids = tabs.getChildAt(1);
        solo.clickOnView(myBids);
        solo.assertCurrentActivity("Wrong Activity", MyBids.class);

        //Switch to My Assigned Jobs screen
        ViewGroup tabs1 = (ViewGroup) solo.getView(R.id.tablayout_id);
        View myAssign = tabs1.getChildAt(2);
        solo.clickOnView(myAssign);
        solo.assertCurrentActivity("Wrong Activity", MyAssigned.class);

        //Switch to All Bids screen
        ViewGroup tabs2 = (ViewGroup) solo.getView(R.id.tablayout_id);
        View allbids = tabs2.getChildAt(3);
        solo.clickOnView(allbids);
        solo.assertCurrentActivity("Wrong Activity", AllBids.class);

        //Switch to My Jobs screen
        ViewGroup tabs3 = (ViewGroup) solo.getView(R.id.tablayout_id);
        View mybids = tabs3.getChildAt(3);
        solo.clickOnView(mybids);
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
