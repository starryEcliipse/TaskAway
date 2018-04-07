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

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }


}
