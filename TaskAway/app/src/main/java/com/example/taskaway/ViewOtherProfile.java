/*
 * Copyright (c) 2018 Team 19, CMPUT301. University of Alberta - All rights reserved.
 * You may use distribute and modify this code under terms and conditions of Code of Student Behavior at
 * University of Alberta
 * You can find a copy of this license in this project. Otherwise please contact contact@abc.ca // TODO contact info
 *
 */

package com.example.taskaway;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 *
 * View shown when username
 *
 * @author  Punam Woosaree
 *
 * @see ViewProfile
 */
public class ViewOtherProfile extends AppCompatActivity {
    private ImageButton toolBarBackbtn;
    private TextView toolBarTitle;
    private ImageButton toolBarSaveBtn;
    private ImageButton editbtn;

    /**
     * Sets layout and TextViews for this activity.
     *
     * @param savedInstanceState - saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

         /* REMOVE TOOLBAR SAVE BUTTON FOR THIS ACTIVITY */
        toolBarSaveBtn = (ImageButton)findViewById(R.id.toolbar_save_btn);
        toolBarSaveBtn.setVisibility(View.GONE);

        /* REMOVE TOOLBAR BACK BUTTON FOR THIS ACTIVITY */
        toolBarSaveBtn = (ImageButton)findViewById(R.id.toolbar_back_btn);
        toolBarSaveBtn.setVisibility(View.GONE);

        /* SET TITLE OF TOOLBAR */
        toolBarTitle = (TextView)findViewById(R.id.toolbar_title);
        toolBarTitle.setText("User Profile");
        /* REMOVE EDIT BUTTON  */
        editbtn = (ImageButton)findViewById(R.id.profile_editbtn);
        editbtn.setVisibility(View.GONE);




        //Get user ID
        final String userID = getIntent().getStringExtra("user_id");

        User current_user = ServerWrapper.getUserFromId(userID);

        TextView usernameTextView = (TextView)findViewById(R.id.profile_name);
        usernameTextView.setText(current_user.getUsername());

        // Set phone number info on screen
        if(current_user.getPhone()!=null) {
            String phonenumber = current_user.getPhone();
            String phonenumb = PhoneNumberUtils.formatNumber(phonenumber);
            TextView phoneTextView = (TextView)findViewById(R.id.profile_phonenumber);
            phoneTextView.setText(phonenumb);
        }

        // Set email info on screen
        if(current_user.getEmail()!=null) {
            String email = current_user.getEmail();
            TextView emailTextView = (TextView) findViewById(R.id.profile_email);
            emailTextView.setText(email);
        }

    }


    /**
     * Calls superclass onStart
     *
     * @see AppCompatActivity
     */
    @Override
    protected void onStart(){
        super.onStart();
    }

    /**
     * Calls superclass onDestroy
     *
     * @see AppCompatActivity
     */
    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
}


