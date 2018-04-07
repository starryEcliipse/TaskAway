/*
 * Copyright (c) 2018 Team X, CMPUT301. University of Alberta - All rights reserved.
 * You may use distribute and modify this code under terms and conditions of Code of Student Behavior at
 * University of Alberta
 * You can find a copy of this license in this project. Otherwise please contact contact@abc.ca
 * /
 *
 * @ Author: Punam Woosaree
 *
 * View shown when user clicks on username
 */

package com.example.taskaway;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.widget.TextView;

public class ViewOtherProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_other_profile);

        //Get user_id and user_name
        final String user_name = getIntent().getStringExtra("user_name");
        final String userID = getIntent().getStringExtra("user_id");

        Log.i("We got here:", userID);

        User current_user = ServerWrapper.getUserFromId(userID);

        TextView usernameTextView = (TextView)findViewById(R.id.editOtherName);
        usernameTextView.setText(current_user.getUsername());

        if(current_user.getPhone()!=null) {
            String phonenumber = current_user.getPhone();
            String phonenumb = PhoneNumberUtils.formatNumber(phonenumber);
            TextView phoneTextView = (TextView)findViewById(R.id.editOtherPhoneNumber);
            phoneTextView.setText(phonenumb);
        }

        if(current_user.getEmail()!=null) {
            String email = current_user.getEmail();
            TextView emailTextView = (TextView) findViewById(R.id.editOtherEmail);
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


