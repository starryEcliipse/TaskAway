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

public class ViewOwnOtherTasksTest extends ActivityInstrumentationTestCase2<ViewOwnTask> {

    private Solo solo;

    public ViewOwnOtherTasksTest() {
        super(com.example.taskaway.ViewOwnTask.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception{
        Activity activity = getActivity();
    }

    public void testViewOwnOtherTask(){
        Login activity = (Login) solo.getCurrentActivity();

        //Register new user
        solo.enterText((EditText) solo.getView(R.id.neweditTextUsername), "firstuser");
        solo.enterText((EditText) solo.getView(R.id.neweditTextPassword), "test");
        solo.clickOnView(solo.getView(R.id.edit_btn));
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        //Select Add Task
        solo.clickOnView(solo.getView(R.id.add_id));
        solo.assertCurrentActivity("Wrong Activity", AddTaskActivity.class);

        //Add sample task
        solo.enterText((EditText) solo.getView(R.id.name_edit_text), "Chores");
        solo.enterText((EditText) solo.getView(R.id.requirements_owntask_text), "Testing is integral to the app");
        solo.enterText((EditText) solo.getView(R.id.location_edit_text), "University of Alberta");
        solo.clickOnView(solo.getView(R.id.toolbar_save_btn));
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        //Logout
        solo.clickOnView(solo.getView(R.drawable.ic_menu));
        solo.clickOnMenuItem("Logout");
        solo.assertCurrentActivity("Wrong Activity", Login.class);

        /*
        //Register new user
        solo.enterText((EditText) solo.getView(R.id.neweditTextUsername), "seconduser");
        solo.enterText((EditText) solo.getView(R.id.neweditTextPassword), "test");
        solo.clickOnView(solo.getView(R.id.newregisterButton));
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        //Switch to All Bids screen and click on other task
        solo.clickInRecyclerView(3);
        solo.assertCurrentActivity("Wrong Activity", AllBids.class);
        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong Activity", ViewTask.class);
*/
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

}
