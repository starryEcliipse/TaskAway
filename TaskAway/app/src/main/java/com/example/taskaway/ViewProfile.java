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

    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

    }

    @Override
    protected void onStart(){
        super.onStart();

        ImageButton editButton = (ImageButton) findViewById(R.id.editProfileButton);

        View v = LayoutInflater.from(ViewProfile.this).inflate(R.layout.activity_edit_profile, null);

        final EditText editName = (EditText) v.findViewById(R.id.editName);
        final EditText editPhone = (EditText) v.findViewById(R.id.editPhoneNumber);
        final EditText editEmail = (EditText) v.findViewById(R.id.editEmail);

        //get a list of all the users from the server
        ArrayList<User> users = ServerWrapper.getAllUsers();

        //iterate through users to find user with matching username
        if(users.size()>0) {
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getUsername().equals(editName.getText().toString())) {
                    user = users.get(i);

                }
            }
        }



        //create editable text views
        editName.setText(user.getUsername().toString(), TextView.BufferType.EDITABLE);
        editPhone.setText(user.getPhone().toString(), TextView.BufferType.EDITABLE);
        editEmail.setText(user.getEmail().toString(), TextView.BufferType.EDITABLE);

        //executed when the edit button is selected
        editButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //create dialog to allow user to edit profile
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewProfile.this);
                builder.setMessage("Edit Profile");
                builder.setView(v);
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
                            user.setUsername(Name);
                            user.setPhone(Phone);
                            user.setEmail(Email);

                            //update the user information in the server
                            ServerWrapper.updateUser(user);

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
