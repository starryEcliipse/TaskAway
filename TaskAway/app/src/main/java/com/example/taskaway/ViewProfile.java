package com.example.taskaway;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Acts a activity that displays user's profile information.
 *
 * Implemented by Punam Woosaree
 */
public class ViewProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        final String userID = getIntent().getStringExtra("user_id");
        final User current_user = ServerWrapper.getUserFromId(userID);

        String username = current_user.getUsername();
        String phonenumber = current_user.getPhone();
        String email = current_user.getEmail();

        TextView usernameTextView = (TextView)findViewById(R.id.editName);
        TextView phoneTextView = (TextView)findViewById(R.id.editPhoneNumber);
        TextView emailTextView = (TextView)findViewById(R.id.editEmail);

        usernameTextView.setText(username);
        phoneTextView.setText(phonenumber);
        emailTextView.setText(email);

        final View view = LayoutInflater.from(ViewProfile.this).inflate(R.layout.activity_edit_profile, null);
        ImageButton editButton = (ImageButton) findViewById(R.id.editProfileButton);
        //executed when the edit button is selected
        editButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {


                final EditText editName = (EditText) view.findViewById(R.id.editName);
                final EditText editPhone = (EditText) view.findViewById(R.id.editPhoneNumber);
                final EditText editEmail = (EditText) view.findViewById(R.id.editEmail);


                //create editable text views
                editName.setText(current_user.getUsername().toString(), TextView.BufferType.EDITABLE);
                editPhone.setText(current_user.getPhone().toString(), TextView.BufferType.EDITABLE);
                editEmail.setText(current_user.getEmail().toString(), TextView.BufferType.EDITABLE);


                //create dialog to allow user to edit profile
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewProfile.this);
                builder.setMessage("Edit Profile");
                builder.setView(view);
                builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //executes if name is at least 8 characters
                        if (!(editName.getText().toString().length()<8)) {

                            //set name,phone and email to the new values
                            String Name = editName.getText().toString();
                            String Phone = editPhone.getText().toString();
                            String Email = editEmail.getText().toString();

                            //update the user information
                            current_user.setUsername(Name);
                            current_user.setPhone(Phone);
                            current_user.setEmail(Email);

                            //update the user information in the server
                            ServerWrapper.updateUser(current_user);

                            //get the profile TextView values
                            TextView usernameTextView = (TextView)findViewById(R.id.editName);
                            TextView phoneTextView = (TextView)findViewById(R.id.editPhoneNumber);
                            TextView emailTextView = (TextView)findViewById(R.id.editEmail);

                            //set the TextViews to their updated values
                            usernameTextView.setText(Name);
                            phoneTextView.setText(Phone);
                            emailTextView.setText(Email);


                        }
                        else {
                            //executes if the name is less than 8 characters
                            Toast.makeText(getApplicationContext(), "Username must be at least 8 characters!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                //Sets up cancel option for user
                builder.setNegativeButton("Cancel", null);
                builder.setCancelable(false);

                AlertDialog alert = builder.create();
                alert.show();
            }});

    }

    @Override
    protected void onStart(){
        super.onStart();

        final String userID = getIntent().getStringExtra("user_id");
        final User current_user = ServerWrapper.getUserFromId(userID);

        final View view = LayoutInflater.from(ViewProfile.this).inflate(R.layout.activity_edit_profile, null);
        ImageButton editButton = (ImageButton) findViewById(R.id.editProfileButton);
        //executed when the edit button is selected
        editButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {


                final EditText editName = (EditText) view.findViewById(R.id.editName);
                final EditText editPhone = (EditText) view.findViewById(R.id.editPhoneNumber);
                final EditText editEmail = (EditText) view.findViewById(R.id.editEmail);


                //create editable text views
                editName.setText(current_user.getUsername().toString(), TextView.BufferType.EDITABLE);
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

                        //executes if name is at least 8 characters
                        if (!(editName.getText().toString().length()<8)) {

                            //set name,phone and email to the new values
                            String Name = editName.getText().toString();
                            String Phone = editPhone.getText().toString();
                            String Email = editEmail.getText().toString();

                            //update the user information
                            current_user.setUsername(Name);
                            current_user.setPhone(Phone);
                            current_user.setEmail(Email);

                            //update the user information in the server
                            ServerWrapper.updateUser(current_user);

                            //get the profile TextView values
                            TextView usernameTextView = (TextView)findViewById(R.id.editName);
                            TextView phoneTextView = (TextView)findViewById(R.id.editPhoneNumber);
                            TextView emailTextView = (TextView)findViewById(R.id.editEmail);

                            //set the TextViews to their updated values
                            usernameTextView.setText(Name);
                            phoneTextView.setText(Phone);
                            emailTextView.setText(Email);


                        }
                        else {
                            //executes if the name is less than 8 characters
                            Toast.makeText(getApplicationContext(), "Username must be at least 8 characters!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                //Sets up cancel option for user
                builder.setNegativeButton("Cancel", null);
                builder.setCancelable(false);

                AlertDialog alert = builder.create();
                alert.show();
            }});



    }



    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
}
