/*
 * Copyright (c) 2018 Team X, CMPUT301. University of Alberta - All rights reserved.
 * You may use distribute and modify this code under terms and conditions of Code of Student Behavior at
 * University of Alberta
 * You can find a copy of this license in this project. Otherwise please contact contact@abc.ca
 * /
 */

package com.example.taskaway;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.EditText;

import com.robotium.solo.Solo;

import com.example.taskaway.MainActivity;
import com.example.taskaway.Login;
import com.example.taskaway.ViewProfile;
import com.example.taskaway.AllBids;
import com.example.taskaway.MyAssigned;
import com.example.taskaway.AddTaskActivity;

public class test extends ActivityInstrumentationTestCase2 {

    private Solo solo;

    public test() {
        super(Login.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception{
        Activity activity = getActivity();
    }

    public void testSwitchToProfile(){

        //Set up for test
        Login activity = (Login)solo.getCurrentActivity();

        //Delete user if Exists
        User user = ServerWrapper.getUserFromUsername("TestUser");
        if (user==null){
            ServerWrapper.deleteUser(user);
        }

        //Register User
        solo.assertCurrentActivity("Wrong Activity", Login.class);
        solo.enterText((EditText) solo.getView(R.id.neweditTextUsername), "TestUser");
        solo.enterText((EditText) solo.getView(R.id.neweditTextPassword), "test");
        solo.clickOnButton("Register");
        solo.assertCurrentActivity("Switch to MainActivity", MainActivity.class);

        //Test if activity goes to profile page
        solo.clickOnView(solo.getView(R.drawable.ic_menu));
        solo.clickOnMenuItem("Profile");
        solo.assertCurrentActivity("Wrong Activity", ViewProfile.class);

    }

    public void testSwitchToAllBids(){
        //Set up for test
        Login activity = (Login)solo.getCurrentActivity();

        //Delete user if Exists
        User user = ServerWrapper.getUserFromUsername("TestUser");
        if (user==null){
            ServerWrapper.deleteUser(user);
        }

        //Register User
        solo.assertCurrentActivity("Wrong Activity", Login.class);
        solo.enterText((EditText) solo.getView(R.id.neweditTextUsername), "TestUser");
        solo.enterText((EditText) solo.getView(R.id.neweditTextPassword), "test");
        solo.clickOnButton("Register");
        solo.assertCurrentActivity("Switch to MainActivity", MainActivity.class);

        //Switch to All Bids screen
        // Create tab layout - add fragments
        /*
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout_id);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager_id);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        // Adding Fragment here
        MyJobs myjobs = new MyJobs();
        MyBids mybids = new MyBids();
        MyAssigned myassigned = new MyAssigned();
        AllBids allbids = new AllBids();
        adapter.addFragment(myjobs,"My Jobs");
        adapter.addFragment(mybids, "My Bids");
        adapter.addFragment(myassigned,"Assigned Jobs");
        adapter.addFragment(allbids,"All Jobs");

        // Setup adapters
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        */

        TabLayout tabLayout = (TabLayout) solo.getView(R.id.tablayout_id);
        TabLayout.Tab tab = tabLayout.getTabAt(3);
        tab.select();
        solo.assertCurrentActivity("Wrong Activity", AllBids.class);


    }

    public void testSwitchToAddTask(){
        //Set up for test
        Login activity = (Login)solo.getCurrentActivity();

        //Delete user if Exists
        User user = ServerWrapper.getUserFromUsername("TestUser");
        if (user==null){
            ServerWrapper.deleteUser(user);
        }

        //Register User
        solo.assertCurrentActivity("Wrong Activity", Login.class);
        solo.enterText((EditText) solo.getView(R.id.neweditTextUsername), "TestUser");
        solo.enterText((EditText) solo.getView(R.id.neweditTextPassword), "test");
        solo.clickOnButton("Register");
        solo.assertCurrentActivity("Switch to MainActivity", MainActivity.class);

        solo.clickOnView(solo.getView(R.id.add_id));
        solo.assertCurrentActivity("Wrong Activity", AddTaskActivity.class);

    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
