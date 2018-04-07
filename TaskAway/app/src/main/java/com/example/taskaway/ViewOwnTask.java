package com.example.taskaway;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

/**
 * Acts as activity that displays a tasks information when a user selects a task *they have created*.
 * DIFFERENT from ViewTask as this implements an Edit button.
 *
 * @author Diane Boytang, Katherine Mae Patenio, Jonathan Ismail
 * Created on 2018-03-14
 *
 * @see AllBids
 * @see AllBidsListViewAdapter
 * @see MyBids
 * @see MyBidsListViewAdapter
 */

public class ViewOwnTask extends AppCompatActivity {

    private TextView taskname;
    private TextView taskstatus;
    private TextView taskdescription;
    private TextView tasklocation;
    private TextView tasklowestbidusername;
    private TextView tasklowestbidamount;
    private Task task;
    private Bid tasklowestbid;
    private ImageButton toolBarBackbtn;
    private TextView toolBarTitle;
    private ImageButton toolBarSaveBtn;
    String id;
    String userID;
    String userName;

    /**
     * Creates TextView, EditView, and Button layouts. Also determines button behaviour.
     *
     * @param savedInstanceState - saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_own_task);

        // Text layouts
        taskname = (TextView) this.findViewById(R.id.my_task_name);
        taskstatus = (TextView) this.findViewById(R.id.my_task_status);
        taskdescription = (TextView) this.findViewById(R.id.my_task_details);
        tasklocation = (TextView) this.findViewById(R.id.my_task_location);


        /* TODO: use with elasticsearch:
            userid = task.getCreatorId();
            user = ServerWrapper.getUserFromId(userid);
            lowest
         */

        // TOOL BAR GO BACK TO MAIN ACTIVITY
        toolBarBackbtn = (ImageButton)findViewById(R.id.toolbar_back_btn);
        toolBarBackbtn.setOnClickListener(new View.OnClickListener() {

            /**
             * Goes back to Main Activity.
             *
             * Note: If finish() is used, we will end up accidentally going back to the
             * ViewOtherBids activity. We do not want this, especially if there are no more bids!
             *
             * @author Katherine Mae Patenio
             *
             * @param v - instance of View
             */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewOwnTask.this, MainActivity.class);
                intent.putExtra("user_name", userName);
                intent.putExtra("user_id", userID);
                Log.i("AddActivity","Sending name and id to MainActivity!");
                startActivity(intent);
            }
        });

        // REMOVE SAVE BUTTON FOR THIS ACTIVITY
        toolBarSaveBtn = (ImageButton)findViewById(R.id.toolbar_save_btn);
        toolBarSaveBtn.setVisibility(View.GONE);

        // SET TITLE OF TOOLBAR
        toolBarTitle = (TextView)findViewById(R.id.toolbar_title);
        toolBarTitle.setText("Your Task");


        // Display lowest bid
        tasklowestbidusername = (TextView) this.findViewById(R.id.lowest_bid_username);
        tasklowestbidamount = (TextView) this.findViewById(R.id.lowest_bid_amount);

        // EDIT BUTTON
        Button editButton = (Button) findViewById(R.id.edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {

            /**
             * Allows user to edit task information or delete task by going to EditActivity.
             * @param view - instance of View
             *
             * @see EditActivity
             * @see SaveFileController
             */
            @Override
            public void onClick(View view) {

                // Get task information
                Intent intent = new Intent(getBaseContext(), EditActivity.class);
                String name = taskname.getText().toString();
                String description = taskdescription.getText().toString();
                String status = taskstatus.getText().toString();

                // Pass relevant information to EditActivity via SaveFileController
                final Context context = getApplicationContext();
                final SaveFileController saveFileController = new SaveFileController();
                final int userIndex = saveFileController.getUserIndex(context, userName);

                // id of task
                id = task.getId();

                // Send task info
                String index = Integer.toString(userIndex);
                intent.putExtra("name", name);
                intent.putExtra("des", description);
                intent.putExtra("status", status);
                intent.putExtra("userindex", index);
                intent.putExtra("task_id", id);

                Log.i("ViewOwnTask","User index is "+index);

                // Send user info
                intent.putExtra("userid", userID);
                intent.putExtra("userName", userName);

                startActivity(intent);
            }
        });

        //Location Details
        Button locationButton = (Button) findViewById(R.id.location_detail_button);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get task information
                Intent intent = new Intent(getBaseContext(), MapActivity.class);
                String name = taskname.getText().toString();
                String location = tasklocation.getText().toString();
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

        // OTHER BIDS BUTTON
        Button otherbidsButton = (Button) findViewById(R.id.other_bids_button);
        otherbidsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // If bids exist, go to ViewOtherBids Activity
                if ((task.getBids() != null) && (!task.getBids().isEmpty())) {

                    // Pass relevant information to EditActivity via SaveFileController
                    final Context context = getApplicationContext();
                    final SaveFileController saveFileController = new SaveFileController();
                    final int userIndex = saveFileController.getUserIndex(context, userName);

                    id = task.getId();

                    String name = taskname.getText().toString();

                    Intent intent = new Intent(ViewOwnTask.this, ViewOtherBids.class);
                    intent.putExtra("name", name);
                    intent.putExtra("username", userName);
                    intent.putExtra("taskid", id);
                    //intent.putExtra("lowestbidamount",tasklowestbid.getAmount());
                    startActivity(intent);
                }

                // No bids
                else if ((task.getBids() == null) || (task.getBids().isEmpty())){
                    Toast.makeText(ViewOwnTask.this, "Your task currently has no bids!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Display task information. Also receive user information.
     *
     */
    @Override
    protected void onStart() {
        super.onStart();
        // TODO: read from server
        Intent intent = getIntent(); // receive task
        task = (Task) intent.getSerializableExtra("task");
        userID = intent.getStringExtra("userid");
        Log.i("userID", ""+userID);
        userName = intent.getStringExtra("userName");
        //float lowestbidamount =
        Log.i("username", ""+ userName);

        // read and display task info
        taskname.setText(task.getName());
        taskstatus.setText(task.getStatus());
        tasklocation.setText(task.getLocation());
        taskdescription.setText(task.getDescription());

        try {
            if (task.getBids().isEmpty()) {
                tasklowestbidamount.setText("No bids yet!");
            } else {
                tasklowestbid = task.findLowestBid();
                tasklowestbidamount.setText(String.format("$%.2f", tasklowestbid.getAmount()));

                // Get username of bidder with lowest bid
                SaveFileController saveFileController = new SaveFileController();
                Context context = getApplicationContext();
                User userBidder = saveFileController.getUserFromUserId(context,tasklowestbid.getUserId());

                String userBidderName = userBidder.getUsername();
                tasklowestbidusername.setText(userBidderName);
            }
        } catch (Exception e) {
            tasklowestbidamount.setText("No bids yet!");
        }
    }

    /**
     * Calls superclass onDestroy
     *
     * @see AppCompatActivity
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}

