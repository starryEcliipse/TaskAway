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

/**
 * Acts a activity that displays user's profile information.
 */
public class ViewProfile extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        final EditText editName = (EditText) ViewProfile.findViewById(R.id.editName);
        final EditText editPhoneNumber = (EditText) ViewProfile.findViewById(R.id.editPhoneNumber);
        final EditText editEmail = (EditText) ViewProfile.findViewById(R.id.editEmail);

    }

    @Override
    protected void onStart(){
        super.onStart();

        final EditText editName = (EditText) ViewProfile.findViewById(R.id.editName);
        final EditText editPhone = (EditText) ViewProfile.findViewById(R.id.editPhoneNumber);
        final EditText editEmail = (EditText) ViewProfile.findViewById(R.id.editEmail);

        ImageButton editButton = (ImageButton) findViewById(R.id.editProfileButton);
        editButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //inflate the edit_view to gather information
                View view = LayoutInflater.from(ViewProfile.this).inflate(R.layout.activity_view_profile, null);

            //create editable text views
                editName.setText(User.getUsername().toString(), TextView.BufferType.EDITABLE);
                editPhone.setText(User.getPhone().toString(), TextView.BufferType.EDITABLE);
                editEmail.setText(User.getEmail().toString(), TextView.BufferType.EDITABLE);

                //create dialog to allow user to edit subscription
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewProfile.this);
                builder.setMessage("Edit Profile");
                builder.setView(v);


                builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //executes if name, charge and date are not empty fields
                        if (!(editName.getText().toString().equals("")) && !(editPhone.getText().toString().equals("")) && !(editEmail.getText().toString().equals(""))) {
                            //set name,phone and email to the new values
                            String Name = editName.getText().toString();
                            String Phone = editPhone.getText().toString();
                            String Email = editEmail.getText().toString();


                            //update the current subscription to the new values
                            User.setUsername(Name);
                            User.setPhone(Phone);
                            User.setEmail(Email);

                        }
                        else {
                            //execute if either the name, date or charge is left blank
                            Toast.makeText(getApplicationContext(), "Make sure Name, Phone number and Email are not blank", Toast.LENGTH_SHORT).show();
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
