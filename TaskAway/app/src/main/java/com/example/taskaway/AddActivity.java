package com.example.taskaway;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Sameerah Wajahat
 * This class handles adding a new task
 */

public class AddActivity extends AppCompatActivity {

    private EditText nameField;
    private EditText requirementField;
    private EditText statusField;
    private Button cancelButton;
    private Button saveButton;
    private final String text = "task";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        nameField = (EditText) findViewById(R.id.editText2);
        requirementField = (EditText) findViewById(R.id.editText3);
        statusField = (EditText) findViewById(R.id.editText);

        saveButton = (Button) findViewById(R.id.button2);

        cancelButton = (Button) findViewById(R.id.button);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameField.getText().toString();
                if (name.isEmpty()) {
                    nameField.setError("Enter name");
                    return;
                }

                if (name.length() > 30){
                    nameField.setError("Name too long");
                    return;
                }

                String comment = requirementField.getText().toString();
                if (comment.isEmpty()){
                    requirementField.setError("Enter requirement");
                    return;
                }

                if (comment.length()>300) {
                    requirementField.setError("Description too long");
                    return;
                }

                String s = statusField.getText().toString();
                if (s.isEmpty()){
                    statusField.setError("Assign status");
                    return;
                }

                // READ USERNAME AND USER ID FROM MAIN
                Intent intent = getIntent();
                String username = intent.getStringExtra("username");
                String userid = intent.getStringExtra("userid");

                // NEW TASK - create with valid input
                Task task = new Task(name, comment, s, null, null, null, null);

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

                //getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();


            }
        });
    }




}
