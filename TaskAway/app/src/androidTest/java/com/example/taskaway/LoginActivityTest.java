package com.example.taskaway;

/**
 * Created by PunamWoosaree on 2018-04-05.
 */

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;


public class LoginActivityTest extends ActivityInstrumentationTestCase2<Login> {

    private Solo solo;

    public LoginActivityTest() {
        super(com.example.taskaway.Login.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception{
        Activity activity = getActivity();
    }

    public void testLoginNewUser() {
        Login activity = (Login)solo.getCurrentActivity();

        solo.assertCurrentActivity("Wrong Activity", Login.class);

        solo.enterText((EditText) solo.getView(R.id.neweditTextUsername), "testnewuser");

        solo.enterText((EditText) solo.getView(R.id.neweditTextPassword), "test");

        solo.sleep(100000);

        solo.clickOnView(solo.getView(R.id.newregisterButton));
        //solo.clickOnButton("Register");

        solo.clearEditText((EditText) solo.getView(R.id.neweditTextUsername));
        solo.clearEditText((EditText) solo.getView(R.id.neweditTextPassword));

        solo.assertCurrentActivity("Wrong Activity", Login.class);

        assertTrue(solo.waitForText("user_name"));
        assertTrue(solo.waitForText("user_id"));

        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", Login.class);
    }


    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

}
