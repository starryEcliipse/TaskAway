package com.example.taskaway;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;

/**
 *
 * This activity is responsible to adding new tasks for the current user.
 * @author Sameerah Wajahat
 *
 * @see EditActivity
 * @see SaveFileController
 */

public class AddActivity extends AppCompatActivity {

    private EditText nameField;
    private EditText requirementField;
    private EditText statusField;
    private EditText locationField;
    private Button cancelButton;
    private Button saveButton;
    private Button uploadPic;
    private final String text = "task";

    /**
     *
     * Creates EditText fields so that user can input information for a new task.
     * Creates buttons as well.
     * Also determines button behaviours when clicked on.
     *
     * @param savedInstanceState - previously saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // EditText layouts
        nameField = (EditText) findViewById(R.id.editText2);
        requirementField = (EditText) findViewById(R.id.editText3);
        statusField = (EditText) findViewById(R.id.editText);
        locationField = (EditText) findViewById(R.id.editMyTaskLocation);


        // Button layouts
        saveButton = (Button) findViewById(R.id.button2);
        cancelButton = (Button) findViewById(R.id.button);
        uploadPic = (Button) findViewById(R.id.button4);

        // Cancel activity
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        uploadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent0 = new Intent(getBaseContext(), UploadPic.class);
                startActivity(intent0);
            }
        });

        // READ INPUT AND ADD TASK - SAVE BUTTON
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameField.getText().toString();

                // Check task name input
                if (name.isEmpty()) {
                    nameField.setError("Enter name");
                    return;
                }
                if (name.length() > 30){
                    nameField.setError("Name too long");
                    return;
                }
                String comment = requirementField.getText().toString();

                // Check task description input
                if (comment.isEmpty()){
                    requirementField.setError("Enter requirement");
                    return;
                }
                if (comment.length()>300) {
                    requirementField.setError("Description too long");
                    return;
                }
                String s = statusField.getText().toString();

                // Check task status input
                if (s.isEmpty()){
                    statusField.setError("Assign status");
                    return;
                }

                String location = locationField.getText().toString();
                // Check location input
                if(location.isEmpty()){
                    location = "N/A";
                }

                // READ USERNAME AND USER ID FROM MAIN
                Intent intent = getIntent();
                String username = intent.getStringExtra("username");
                String userid = intent.getStringExtra("userid");

                // NEW TASK - create with valid input
                String task_id = Calendar.getInstance().getTime().toString();
                task_id = task_id.replaceAll(" ", "");
                Task task = new Task(name, comment, s, location, null, null, null, task_id);

                // Added by Katherine Mae Patenio March 30 2018
                // Include current user's ID into the task so that we can identify to whom the task
                // belongs to later on
                task.setCreatorId(userid);

                // SAVE TO FILE TODO: ELASTICSEARCH
                final Context context = getApplicationContext();
                SaveFileController saveFileController = new SaveFileController();
                int userindex = saveFileController.getUserIndex(context, username); // get userindex
                Log.i("AddActivity","userindex is "+userindex);
                saveFileController.addRequiredTask(context, userindex, task); // add task to proper user in savefile


                // GO TO MAIN ACTIVITY
                Intent intent2 = new Intent(AddActivity.this, MainActivity.class);
                intent2.putExtra("user_name", username);
                intent2.putExtra("user_id", userid);
                Log.i("AddActivity","Sending name and id to MainActivity!");
                startActivity(intent2);

            } // end of onClick
        });
    } // end of onCreate()

}
