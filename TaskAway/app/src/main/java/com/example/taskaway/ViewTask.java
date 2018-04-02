package com.example.taskaway;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Acts as activity that displays a tasks information when a user selects a task
 * that they HAVE NOT previously bid on.
 *
 * @author Diane Boytang
 *
 * @see AllBids
 * @see MyBids
 */

public class ViewTask extends AppCompatActivity {
    //Declare vars
    private TextView taskname;
    private TextView taskstatus;
    private TextView taskdescription;
    private TextView tasklocation;
    private TextView taskwinningbid;
    private EditText userbid;
    private Task task;
    private Bid winningbid;
    private float bidamount;
    String id;
    String userID;
    String userName;
    private ArrayList<Bid> bidList;



    /**
     * Creates TextView, EditView, and Button layouts. Also determines button behaviour.
     *
     * @param savedInstanceState - saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);

        // Text layouts
        taskname = (TextView) this.findViewById(R.id.task_name);
        taskdescription = (TextView) this.findViewById(R.id.task_details);
        taskstatus = (TextView) this.findViewById(R.id.task_status);
        tasklocation = (TextView) this.findViewById(R.id.task_location);
        taskwinningbid = (TextView) this.findViewById(R.id.winning_bid_amount);
        userbid = (EditText) this.findViewById(R.id.your_bid_amount);


        // SAVE BUTTON - place a bid
        Button saveButton = (Button) findViewById(R.id.save_button_1);
        saveButton.setOnClickListener(new View.OnClickListener() {

            /**
             * Allows user to place a bid on another user's task once the save button is selected.
             * Updates list of bids for a certain task for task requester.
             * Updates list of tasks task provider has bidded on
             *
             * @param view - instance of View
             * @author Katherine Mae Patenio, Diane Boytang
             * @see SaveFileController
             * @see MyBids
             */
            @Override
            public void onClick(View view) {
                String inputbid = userbid.getText().toString();

                // Check if input is valid
                try {
                    // No bid entered
                    if (inputbid.isEmpty()) {
                        userbid.setError("No bid entered!");
                        return;
                    }

                    bidamount = Float.parseFloat(inputbid);

                    // Not a decimal/float
                } catch (Exception e) {
                    Log.i("ViewTask", "Invalid bid entered!");
                    userbid.setError("Invalid bid entered!");
                    return;
                } // end of catch

                /* ADDING NEW BID STARTS HERE */
                Bid bid = new Bid(userID, bidamount);

                // If no bid list exists for a task, create a new one
                if (task.getBids() == null) {
                    Log.i("ViewTask", "task.getBids() is NULL");

                    // Make new arraylist to contain bids
                    bidList = new ArrayList<Bid>();
                    bidList.add(bid);
                    task.setBids(bidList);


                }
                // Else, update current one
                else {
                    Log.i("ViewTask", "task.getBids() is NOT NULL");

                    bidList = task.getBids();
                    bidList.add(bid);
                    task.setBids(bidList);
                }

                /*
                // Just for reading all bids in IDE Console - for debugging purposes
                for (Bid temp : task.getBids()) {
                    Log.i("ViewTask", "Reading: " + temp.getAmount());
                } */


                // SAVEFILECONTROLLER FOR UPDATING THE TASK'S LIST OF BIDS
                final Context context = getApplicationContext();
                SaveFileController saveFileController = new SaveFileController();
                // get userindex of the task requester
                int userindexCreator = saveFileController.getUserIndexFromCreatorID(context, task.getCreatorId());
                saveFileController.updateTask(context, userindexCreator, task.getId(), task);


                // SAVEFILECONTROLLER FOR UPDATING MYBIDS MENU
                // A task the user has bid on should now appear in the middle menu
                int userindexBidder = saveFileController.getUserIndex(context, userName);

                saveFileController.addBiddedTask(context, userindexBidder, task);
                saveFileController.updateTaskBids(context, userindexCreator, task, task.getId(), bid);

                // GO BACK TO MAIN
                Intent intent2 = new Intent(ViewTask.this, MainActivity.class);
                intent2.putExtra("user_name", userName);
                intent2.putExtra("user_id", userID);
                Log.i("ViewTask", "Sending name and id to MainActivity!");
                startActivity(intent2);
            }
        });

        // CANCEL BUTTON - cancel activity
        Button cancelButton = (Button) findViewById(R.id.cancel_button_1);
        cancelButton.setOnClickListener(new View.OnClickListener() {

            /**
             * Cancels activity when cancel button selected by user.
             *
             * @param view - instance of View
             */
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Location Details
        Button locationButton = (Button) findViewById(R.id.location_detail_button);
        locationButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Begins MapActivity when the user selects the Location Details button.
             *
             * @author Diane Boytang
             *
             * @param view - instance of View
             *
             * @see MapActivity
             * @see SaveFileController
             */
            @Override
            public void onClick(View view) {

                // Get task information
                Intent intent = new Intent(getBaseContext(), MapActivity.class);
                String name = taskname.getText().toString();
                String location = tasklocation.getText().toString();

                // If no location is entered by the user
                if(location.equals("N/A")){
                    return;
                }

                id = task.getId();

                // Pass relevant information to MapActivity via SaveFileController
                final Context context = getApplicationContext();
                final SaveFileController saveFileController = new SaveFileController();
                final int userIndex = saveFileController.getUserIndex(context, userName);

                // Send task info
                String index = Integer.toString(userIndex);
                intent.putExtra("name", name);
                intent.putExtra("location", location);
                intent.putExtra("task_id", id);


                // Send user info
                intent.putExtra("userid", userID);
                intent.putExtra("userName", userName);

                startActivity(intent);
            }
        });
    }

    /**
     * Displays task information and updates layout. Also receive username and user id.
     *
     */
    @Override
    protected void onStart(){
        super.onStart();
        Log.i("ViewTask","Viewing task!");
        //TODO: Read from server

        // Get task information
        Intent intent = getIntent();
        task = (Task) intent.getSerializableExtra("task");

        Log.i("ViewTask","Now reading task id: "+task.getId());

        // Read and display task info
        taskname.setText(task.getName());
        taskstatus.setText(task.getStatus());
        tasklocation.setText(task.getLocation());
        taskdescription.setText(task.getDescription());

        // Get user information
        userID = intent.getStringExtra("userid");
        Log.i("userID", userID);
        userName = intent.getStringExtra("userName"); // FIXME username vs userName
        Log.i("userName", userName);

        // Bid information
        try {
            if (task.getBids().isEmpty()) {
                taskwinningbid.setText("No bids yet!");
            } else {
                winningbid = task.findLowestBid();
                taskwinningbid.setText(String.valueOf(winningbid.getAmount()));
            }
        }
        catch (Exception e){
            taskwinningbid.setText("No bids yet!");
        }
    }

    /**
     * Calls superclass's onDestroy.
     *
     * @see AppCompatActivity
     */
    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
}
