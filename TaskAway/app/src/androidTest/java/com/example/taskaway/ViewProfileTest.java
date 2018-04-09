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
import com.example.taskaway.Login;
import com.example.taskaway.ViewProfile;
import com.example.taskaway.MainActivity;

import com.robotium.solo.Solo;

public class ViewProfileTest extends ActivityInstrumentationTestCase2{

    private Solo solo;

    public ViewProfileTest() {
        super(Login.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception{
        Activity activity = getActivity();
    }

    //Create new user Profile
    public void testViewOwnProfile(){
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

    public void testEditOwnProfile(){
        Login activity = (Login)solo.getCurrentActivity();

        User user = ServerWrapper.getUserFromUsername("TestUser");
        if (user!=null){
            solo.enterText((EditText) solo.getView(R.id.neweditTextUsername), "TestUser");
            solo.enterText((EditText) solo.getView(R.id.neweditTextPassword), "test");
            solo.clickOnButton("Register");
        }else{
            solo.enterText((EditText) solo.getView(R.id.neweditTextUsername), "TestUser");
            solo.enterText((EditText) solo.getView(R.id.neweditTextPassword), "test");
            solo.clickOnButton("Login");
        }

        //Test if on MainActivity Page
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        //Test if activity goes to profile page
        solo.clickOnActionBarItem(1);
        solo.assertCurrentActivity("Wrong Activity", ViewProfile.class);


        solo.clickOnView(solo.getView(R.id.editProfileButton));
        solo.enterText((EditText) solo.getView(R.id.editPhoneNumber), "7801001000");
        solo.enterText((EditText) solo.getView(R.id.editEmail), "hello@email.com");
        solo.clickOnButton("Edit");
        solo.assertCurrentActivity("Wrong Activity", ViewProfile.class);

    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

}
