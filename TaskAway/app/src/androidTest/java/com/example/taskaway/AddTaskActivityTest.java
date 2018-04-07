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

public class AddTaskActivityTest extends ActivityInstrumentationTestCase2<AddTaskActivity> {
    private Solo solo;

    public AddTaskActivityTest() {
        super(com.example.taskaway.AddTaskActivity.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception{
        Activity activity = getActivity();
    }

    /**
     * Tests all valid and non valid input in creating a task
     */
    public void testAddTask(){
        AddTaskActivity activity = (AddTaskActivity) solo.getCurrentActivity();

        //Test no input
        solo.clickOnView(solo.getView(R.id.toolbar_save_btn));
        solo.assertCurrentActivity("Wrong Activity", AddTaskActivity.class);

        //Test no name input
        solo.enterText((EditText) solo.getView(R.id.requirements_edit_text), "Just testing purposes");
        solo.clickOnView(solo.getView(R.id.toolbar_save_btn));
        solo.assertCurrentActivity("Wrong Activity", AddTaskActivity.class);

        //Test name less than 8 characters
        solo.enterText((EditText) solo.getView(R.id.name_edit_text), "Chores");
        solo.clickOnView(solo.getView(R.id.toolbar_save_btn));
        solo.assertCurrentActivity("Wrong Activity", AddTaskActivity.class);

        //Test no description given
        solo.clearEditText((EditText) solo.getView(R.id.requirements_edit_text));
        solo.clickOnView(solo.getView(R.id.toolbar_save_btn));
        solo.assertCurrentActivity("Wrong Activity", AddTaskActivity.class);

        //Test description greater than 300
        solo.enterText((EditText) solo.getView(R.id.requirements_edit_text), "So no one told you life was going to be this way. Your job's a joke, you're broke, you're love life's DOA. It's like you're always stuck in second gear, Well, it hasn't been your day, your week, your month, or even your year. But, I'll be there for you, when the rain starts to pour. I'll be there for you, like I've been there before. ");
        solo.clickOnView(solo.getView(R.id.toolbar_save_btn));
        solo.assertCurrentActivity("Wrong Activity", AddTaskActivity.class);

        //Test all correct inputs
        solo.enterText((EditText) solo.getView(R.id.requirements_edit_text), "Testing is integral to the app");
        solo.enterText((EditText) solo.getView(R.id.location_edit_text), "University of Alberta");
        solo.clickOnView(solo.getView(R.id.toolbar_save_btn));
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);





    }

    /**
     * Tests all the EditTestActivity constraints
     */
    public void testEditTask(){
        AddTaskActivity activity = (AddTaskActivity) solo.getCurrentActivity();

        //Sample Task to edit
        solo.enterText((EditText) solo.getView(R.id.name_edit_text), "Chores");
        solo.enterText((EditText) solo.getView(R.id.requirements_edit_text), "Testing is integral to the app");
        solo.enterText((EditText) solo.getView(R.id.location_edit_text), "University of Alberta");
        solo.clickOnView(solo.getView(R.id.toolbar_save_btn));
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        //Check if can Edit a task
        solo.clickInRecyclerView(0);
        solo.assertCurrentActivity("Wrong Activity", EditActivity.class);

        //Test no input
        solo.clickOnView(solo.getView(R.id.toolbar_save_btn));
        solo.assertCurrentActivity("Wrong Activity", EditActivity.class);

        //Test no name input
        solo.enterText((EditText) solo.getView(R.id.requirements_edit_text), "Just testing purposes");
        solo.clickOnView(solo.getView(R.id.toolbar_save_btn));
        solo.assertCurrentActivity("Wrong Activity", EditActivity.class);

        //Test name less than 8 characters
        solo.enterText((EditText) solo.getView(R.id.name_edit_text), "Chores");
        solo.clickOnView(solo.getView(R.id.toolbar_save_btn));
        solo.assertCurrentActivity("Wrong Activity", EditActivity.class);

        //Test no description given
        solo.clearEditText((EditText) solo.getView(R.id.requirements_edit_text));
        solo.clickOnView(solo.getView(R.id.toolbar_save_btn));
        solo.assertCurrentActivity("Wrong Activity", EditActivity.class);

        //Test description greater than 300
        solo.enterText((EditText) solo.getView(R.id.requirements_edit_text), "So no one told you life was going to be this way. Your job's a joke, you're broke, you're love life's DOA. It's like you're always stuck in second gear, Well, it hasn't been your day, your week, your month, or even your year. But, I'll be there for you, when the rain starts to pour. I'll be there for you, like I've been there before. ");
        solo.clickOnView(solo.getView(R.id.toolbar_save_btn));
        solo.assertCurrentActivity("Wrong Activity", EditActivity.class);

        //Correct inputs for edited Task
        solo.enterText((EditText) solo.getView(R.id.name_edit_text), "Homework");
        solo.enterText((EditText) solo.getView(R.id.requirements_edit_text), "Testing is integral to the app");
        solo.enterText((EditText) solo.getView(R.id.location_edit_text), "University of Alberta");
        solo.clickOnView(solo.getView(R.id.toolbar_save_btn));
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        //Check if can Delete a task
        solo.clickInRecyclerView(0);
        solo.assertCurrentActivity("Wrong Activity", EditActivity.class);
        solo.clickOnView(solo.getView(R.id.DeleteButton));
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);


    }

    /**
     * Tests the back button
     */
    public void testBackOption(){
        AddTaskActivity activity = (AddTaskActivity) solo.getCurrentActivity();
        solo.clickOnView(solo.getView(R.id.toolbar_back_btn));
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

    }

    /**
     * Tests the switch to the upload pic activity
     */
    public void testUploadPic(){
        AddTaskActivity activity = (AddTaskActivity) solo.getCurrentActivity();
        solo.clickOnView(solo.getView(R.id.image_camera_btn));
        solo.assertCurrentActivity("Wrong Activity", UploadPic.class);

    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
