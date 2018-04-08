
package com.example.taskaway;
import android.support.v7.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 *
 * This class handles editing / deleting an existing task.
 *
 * @see Task
 * @see ViewOwnTask
 * @author Sameerah Wajahat
 *
 */
public class EditActivity extends AppCompatActivity {
    private EditText tname;
    private EditText des;
    private EditText status;
    private EditText location;
    private Button cancel;
    private Button delete;
    private Button save;
    private Task task;
    private String new_name;
    private String new_des;
    private String new_status;
    private String new_location;
    String userName;
    String user_id;
    String task_id;
    String creator_id;
    String assigned_id;
    int index;
    private ImageButton toolBarSaveBtn;
    private ImageButton uploadPic;
    private ImageButton toolBarBackbtn;

    /**
     * Upon creating activity, create EditText and Button layouts.
     * Also retrieve username and user id information for obtaining appropriate task information.
     * Determine behaviours of buttons.
     *
     * @param savedInstanceState - saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        // EditText layout
        tname = (EditText) findViewById(R.id.name_edit_text);
        des = (EditText) findViewById(R.id.requirements_edit_text);
        //status = (EditText) findViewById(R.id.editText);
        location = (EditText) findViewById(R.id.location_edit_text);

        // Get information needed to update task
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        user_id = intent.getStringExtra("userid");
        task_id = intent.getStringExtra("task_id");

        if (MainActivity.isOnline()){
            task = ServerWrapper.getJobFromId(task_id);
        }else {
            // Get userindex
            final String i = intent.getStringExtra("userindex");
            Log.i("EditActivity", "Received string User Index is " + i);
            index = Integer.parseInt(i);
            Log.i("EditActivity", "User index as int is " + index);

            // SaveFileController - getTask
            final Context context1 = getApplicationContext();
            SaveFileController saveFileController1 = new SaveFileController();
            task = saveFileController1.getTask(context1, index, task_id);
            Log.i("USER INDEX", index + "");
            Log.i("TASK ID:", task_id + "");
        }

        // Get task information
        new_name = task.getName();
        new_des = task.getDescription();
        //new_status = task.getStatus();
        new_location = task.getLocation();

        creator_id = task.getCreatorId();
        assigned_id = task.getAssignedId();

        // setText
        tname.setText(new_name);
        des.setText(new_des);
        //status.setText(new_status);
        location.setText(new_location);


        // Cancel button - cancel activity
        toolBarBackbtn = (ImageButton)findViewById(R.id.toolbar_back_btn);
        toolBarBackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        // Save button - save changes and update task
        toolBarSaveBtn = (ImageButton)findViewById(R.id.toolbar_save_btn);
        toolBarSaveBtn.setOnClickListener(new View.OnClickListener() {
            /**
             * Determines behaviour of save button.
             * Reads inputted text fields (if valid) and updates task information
             *
             * @see SaveFileController
             */
            @Override
            public void onClick(View view) {

                if (task.getBids() == null) {

                    // Task name
                    String name = tname.getText().toString();
                    if (name.isEmpty()) {
                        tname.setError("Enter name");
                        return;
                    }
                    if (name.length() > 30) {
                        tname.setError("Name too long");
                        return;
                    }

                    // Description
                    String comment = des.getText().toString();
                    if (comment.isEmpty()) {
                        des.setError("Enter requirement");
                        return;
                    }
                    if (comment.length() > 300) {
                        des.setError("Description too long");
                        return;
                    }
                    String s = "REQUESTED";

                    // Task status
                    /*
                    String s = status.getText().toString();
                    if (s.isEmpty()) {
                        status.setError("Assign status");
                        return;
                    }
                    if ((!s.equals("REQUESTED")) && (!s.equals("ASSIGNED")) && (!s.equals("BIDDED")) && (!s.equals("DONE"))){
                        status.setError("Invalid status type");
                        return;
                    }*/

                    // Task location
                    String loc = location.getText().toString();
                    if (loc.isEmpty()) {
                        loc = "N/A";
                    }

                    Task task = new Task(name, comment, s, loc, null, null, null, task_id);
                    task.setCreatorId(creator_id);
                    task.setAssignedId(assigned_id);

                    if (MainActivity.isOnline()){
                        task.updateCoordinates();
                        ServerWrapper.updateJob(task);
                        ServerWrapper.syncWithServer(getApplicationContext(), userName);
                    }else{
                        // SAVE TO FILE
                        final Context context = getApplicationContext();
                        SaveFileController saveFileController = new SaveFileController();
                        int userindex = saveFileController.getUserIndex(context, userName); // get userindex

                        if (task.getSyncInstruction().equals("OFFLINE_ADD")){
                            // do not change the instruction, since it has yet to be added to the server
                        }else{
                            task.setSyncInstruction("OFFLINE_UPDATE"); // change instruction to signify it needs to be updated on next login
                        }
                        // Update user information
                        saveFileController.updateTask(context, userindex, task_id, task); // add task to proper user in savefile
                    }

                    // GO TO MAIN ACTIVITY
                    Intent intent2 = new Intent(EditActivity.this, MainActivity.class);
                    intent2.putExtra("user_name", userName);
                    intent2.putExtra("user_id", user_id);
                    //Log.i("AddActivity", "Sending name and id to MainActivity!");
                    startActivity(intent2);
                } // end of if
                else if (task.getBids() != null){
                    Toast.makeText(EditActivity.this, "Unable to save changes: this task already has bids!", Toast.LENGTH_SHORT).show();
                }
            } // end of onclick
        });

        // Delete button - deletes the task being viewed
        RelativeLayout delete = (RelativeLayout) findViewById(R.id.DeleteButton);
        delete.setOnClickListener(new View.OnClickListener() {
            /**
             * Determines delete button behaviour.
             * Deletes the current task. Updates user information.
             *
             * @see SaveFileController
             */
            @Override
            public void onClick(View view) {

                if (MainActivity.isOnline()){
                    ServerWrapper.deleteJob(task);

                    //ServerWrapper.syncWithServer(getApplicationContext(), userName);
                }else{
                    final Context context2 = getApplicationContext();
                    SaveFileController saveFileController2 = new SaveFileController();
                    task.markDeleted();
                    if (task.getSyncInstruction().equals("OFFLINE_ADD")){
                        // Can just delete it locally since it has yet to be added to the server
                        // Update user information - remove task
                        saveFileController2.deleteTask(context2, index, task_id); // add task to proper user in savefile
                        saveFileController2.deleteTaskBids(context2, index, -1, task_id); // update for task providers - delete task
                    }else{
                        task.setSyncInstruction("OFFLINE_DELETE");
                        // Update user information - remove task
                        saveFileController2.updateTask(context2, index, task_id, task); // updates task to proper user in savefile
                    }
                }

                // GO TO MAIN ACTIVITY
                Intent intent2 = new Intent(EditActivity.this, MainActivity.class);
                intent2.putExtra("user_name", userName);
                intent2.putExtra("user_id", user_id);
                //Log.i("AddActivity","Sending name and id to MainActivity!");
                startActivity(intent2);
            }
        });
    } // end of onCreate

    /**
     * Calls superclass onStart.
     *
     * @see AppCompatActivity
     */
    @Override
    protected void onStart(){
        super.onStart();
    }
}
