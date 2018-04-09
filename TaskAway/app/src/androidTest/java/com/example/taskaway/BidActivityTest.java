/*
 * Copyright (c) 2018 Team X, CMPUT301. University of Alberta - All rights reserved.
 * You may use distribute and modify this code under terms and conditions of Code of Student Behavior at
 * University of Alberta
 * You can find a copy of this license in this project. Otherwise please contact contact@abc.ca
 * /
 */

package com.example.taskaway;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;

public class BidActivityTest extends ActivityInstrumentationTestCase2 {

    private Solo solo;

    public BidActivityTest() {
        super(com.example.taskaway.Login.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception{
        Activity activity = getActivity();
    }

    public void testBid(){
        //Delete user if Exists
        User user = ServerWrapper.getUserFromUsername("goodNewUser");
        if (user!=null){
            ServerWrapper.deleteUser(user);
        }

        //Test if correct username and password allows login
        solo.clearEditText((EditText) solo.getView(R.id.neweditTextUsername));
        solo.enterText((EditText) solo.getView(R.id.neweditTextUsername), "goodNewUser");
        solo.enterText((EditText) solo.getView(R.id.neweditTextPassword), "test");
        solo.clickOnButton("Register");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        solo.clickOnView(solo.getView(R.id.add_id));
        solo.assertCurrentActivity("Wrong Activity", AddTaskActivity.class);

        //Correct inputs for edited Task
        solo.enterText((EditText) solo.getView(R.id.name_edit_text), "Homework");
        solo.enterText((EditText) solo.getView(R.id.assigned_requirementsk), "Testing is integral to the app");
        solo.enterText((EditText) solo.getView(R.id.location_edit_text), "University of Alberta");
        solo.clickOnView(solo.getView(R.id.toolbar_save_btn));
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        //Logout
        solo.clickOnActionBarItem(2);
        solo.assertCurrentActivity("Wrong Activity", Login.class);

        //Delete user if Exists
        User user1 = ServerWrapper.getUserFromUsername("seconduser");
        if (user1!=null){
            ServerWrapper.deleteUser(user1);
        }

        //Test if correct username and password allows login
        solo.clearEditText((EditText) solo.getView(R.id.neweditTextUsername));
        solo.enterText((EditText) solo.getView(R.id.neweditTextUsername), "seconduser");
        solo.enterText((EditText) solo.getView(R.id.neweditTextPassword), "test");
        solo.clickOnButton("Register");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        //test search and go to all bids page
        solo.clickOnView(solo.getView(R.id.search_id));
        solo.assertCurrentActivity("Wrong Activity", AllBids.class);

        //Place bid
        solo.clickInRecyclerView(0);
        solo.assertCurrentActivity("Wrong Activity", AllBids.class);

        //Enter no bid and save
        solo.clickOnView(solo.getView(R.id.save_button_2));
        solo.assertCurrentActivity("Wrong Activity", ViewBidTask.class);

        //Enter correct bid
        solo.enterText((EditText) solo.getView(R.id.new_bid_amount), "5.00");
        solo.clickOnView(solo.getView(R.id.save_button_2));
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
    }

    public void testAcceptBid(){
        //Delete user if Exists
        User user = ServerWrapper.getUserFromUsername("goodNewUser");
        if (user!=null){
            ServerWrapper.deleteUser(user);
        }

        //Test if correct username and password allows login
        solo.enterText((EditText) solo.getView(R.id.neweditTextUsername), "goodNewUser");
        solo.enterText((EditText) solo.getView(R.id.neweditTextPassword), "test");
        solo.clickOnButton("Register");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        solo.clickOnView(solo.getView(R.id.add_id));
        solo.assertCurrentActivity("Wrong Activity", AddTaskActivity.class);

        //Correct inputs for edited Task
        solo.enterText((EditText) solo.getView(R.id.name_edit_text), "Homework");
        solo.enterText((EditText) solo.getView(R.id.assigned_requirementsk), "Testing is integral to the app");
        solo.enterText((EditText) solo.getView(R.id.location_edit_text), "University of Alberta");
        solo.clickOnView(solo.getView(R.id.toolbar_save_btn));
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        //Logout
        solo.clickOnActionBarItem(2);
        solo.assertCurrentActivity("Wrong Activity", Login.class);

        //Delete user if Exists
        User user1 = ServerWrapper.getUserFromUsername("seconduser");
        if (user1!=null){
            ServerWrapper.deleteUser(user1);
        }

        //Test if correct username and password allows login
        solo.clearEditText((EditText) solo.getView(R.id.neweditTextUsername));
        solo.enterText((EditText) solo.getView(R.id.neweditTextUsername), "seconduser");
        solo.enterText((EditText) solo.getView(R.id.neweditTextPassword), "test");
        solo.clickOnButton("Register");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        //test search and go to all bids page
        solo.clickOnView(solo.getView(R.id.search_id));
        solo.assertCurrentActivity("Wrong Activity", AllBids.class);

        //Place bid
        solo.clickInRecyclerView(0);
        solo.assertCurrentActivity("Wrong Activity", AllBids.class);

        //Enter no bid and save
        solo.clickOnView(solo.getView(R.id.save_button_2));
        solo.assertCurrentActivity("Wrong Activity", ViewBidTask.class);

        //Enter correct bid
        solo.enterText((EditText) solo.getView(R.id.new_bid_amount), "5.00");
        solo.clickOnView(solo.getView(R.id.save_button_2));
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        //Logout
        solo.clickOnActionBarItem(2);
        solo.assertCurrentActivity("Wrong Activity", Login.class);

        //Login
        solo.enterText((EditText) solo.getView(R.id.neweditTextUsername), "goodNewUser");
        solo.enterText((EditText) solo.getView(R.id.neweditTextPassword), "test");
        solo.clickOnView(solo.getView(R.id.newloginButton));
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        //Accept Bid
        solo.clickInRecyclerView(1);
        solo.clickOnView(solo.getView(R.id.newaccept_button));
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

    }



}
