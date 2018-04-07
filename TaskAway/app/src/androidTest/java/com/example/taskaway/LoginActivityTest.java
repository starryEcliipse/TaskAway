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

    public void testRegister() {
        Login activity = (Login)solo.getCurrentActivity();

        //test user name less than 8 characters
        solo.assertCurrentActivity("Wrong Activity", Login.class);
        solo.enterText((EditText) solo.getView(R.id.neweditTextUsername), "imnew");
        solo.enterText((EditText) solo.getView(R.id.neweditTextPassword), "test");
        solo.sleep(100000);
        solo.clickOnButton("Register");
        solo.assertCurrentActivity("Wrong Activity", Login.class);

        //test for numbers or other non alphabetic characters
        solo.clearEditText((EditText) solo.getView(R.id.neweditTextUsername));
        solo.enterText((EditText) solo.getView(R.id.neweditTextUsername), "1stthebest!");
        solo.sleep(100000);
        solo.clickOnView(solo.getView(R.id.newregisterButton));
        solo.assertCurrentActivity("Wrong Activity", Login.class);

        //Test if correct username and password allows login
        solo.clearEditText((EditText) solo.getView(R.id.neweditTextUsername));
        solo.enterText((EditText) solo.getView(R.id.neweditTextUsername), "goodNewUser");
        solo.sleep(100000);
        solo.clickOnButton("Register");
        solo.assertCurrentActivity("Switch to MainActivity", MainActivity.class);
    }

    public void testLogin(){

        //Set up for test
        Login activity = (Login)solo.getCurrentActivity();
        solo.assertCurrentActivity("Wrong Activity", Login.class);
        solo.enterText((EditText) solo.getView(R.id.neweditTextUsername), "TestUser");
        solo.enterText((EditText) solo.getView(R.id.neweditTextPassword), "test");
        solo.clickOnButton("Register");
        solo.assertCurrentActivity("Switch to MainActivity", MainActivity.class);
        solo.goBack();

        //No Login Info provided
        solo.clickOnView(solo.getView(R.id.newloginButton));
        solo.assertCurrentActivity("Wrong Activity", Login.class);

        //Incorrect Login Info Provided
        solo.enterText((EditText) solo.getView(R.id.neweditTextUsername), "imnotright");
        solo.enterText((EditText) solo.getView(R.id.neweditTextPassword), "test");
        solo.clickOnView(solo.getView(R.id.newloginButton));
        solo.assertCurrentActivity("Wrong Activity", Login.class);

        //Correct Info Provided
        solo.clearEditText((EditText) solo.getView(R.id.neweditTextUsername));
        solo.enterText((EditText) solo.getView(R.id.neweditTextUsername), "TestUser");
        solo.clickOnView(solo.getView(R.id.newloginButton));
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
    }



    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

}
