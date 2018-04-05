package com.example.taskaway;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Acts a activity that displays user's profile information.
 *
 * @author Punam Woosaree
 */

/*
 * DO NOT REMOVE COMMENTS
 * Those lines are for when we have server connectivity
 */
public class ViewProfile extends AppCompatActivity {


    /**
     * Receive username and userid from Login activity to receive user information from server.
     *
     * @param savedInstanceState - saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        //Get user_id and user_name
        final String user_name = getIntent().getStringExtra("user_name");
        final String userID = getIntent().getStringExtra("user_id");

        //Get user from server
        // TODO SaveFileController
        final User current_user_server = ServerWrapper.getUserFromId(userID);

        //Get user from file
        final Context context = getApplicationContext();
        final SaveFileController saveFileController = new SaveFileController();
        final User current_user = saveFileController.getUserFromUsername(context, user_name);
        final Integer userIndex = saveFileController.getUserIndex(context, user_name);

        String username = current_user.getUsername();
        Log.i("User_name:", username);
        TextView usernameTextView = (TextView)findViewById(R.id.editName);
        usernameTextView.setText(username);

        if(current_user.getPhone()!=null) {
            String phonenumber = current_user.getPhone();
            String phonenumb = PhoneNumberUtils.formatNumber(phonenumber);
            TextView phoneTextView = (TextView)findViewById(R.id.editPhoneNumber);
            phoneTextView.setText(phonenumb);
        }

        if(current_user.getEmail()!=null) {
            String email = current_user.getEmail();
            TextView emailTextView = (TextView) findViewById(R.id.editEmail);
            emailTextView.setText(email);
        }

        final View view = LayoutInflater.from(ViewProfile.this).inflate(R.layout.activity_edit_profile, null);
        ImageButton editButton = (ImageButton) findViewById(R.id.editProfileButton);
        //executed when the edit button is selected
        editButton.setOnClickListener(new View.OnClickListener(){
            /**
             * Selecting Edit button allows user to edit user information.
             * @param v
             */
            @Override
            public void onClick(View v) {


                final EditText editPhone = (EditText) view.findViewById(R.id.editPhoneNumber);
                final EditText editEmail = (EditText) view.findViewById(R.id.editEmail);


                //create editable text views

                if(current_user.getPhone()!=null){
                    editPhone.setText(current_user.getPhone().toString(), TextView.BufferType.EDITABLE);
                }
                if(current_user.getEmail()!=null) {
                    editEmail.setText(current_user.getEmail().toString(), TextView.BufferType.EDITABLE);
                }


                //create dialog to allow user to edit profile
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewProfile.this);
                builder.setMessage("Edit Profile");
                builder.setView(view);
                builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        //set phone and email to the new values
                        String Phone = editPhone.getText().toString();
                        String Email = editEmail.getText().toString();

                            //format phone number
                        String phonenum = PhoneNumberUtils.formatNumber(Phone);

                        //update the user information for file
                        current_user.setPhone(phonenum);
                        current_user.setEmail(Email);

                        //update the user information in the server
                        //ServerWrapper.updateUser(current_user);

                        //update information in server
                        saveFileController.updateUser(context, userIndex, current_user);

                        //get the profile TextView values
                        TextView phoneTextView = (TextView)findViewById(R.id.editPhoneNumber);
                        TextView emailTextView = (TextView)findViewById(R.id.editEmail);

                        //set the TextViews to their updated values
                        phoneTextView.setText(Phone);
                        emailTextView.setText(Email);

                    }
                });
                //Sets up cancel option for user
                builder.setNegativeButton("Cancel", null);
                builder.setCancelable(false);

                AlertDialog alert = builder.create();
                alert.show();
            }});

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
