package com.example.taskaway;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Acts as activity that displays a tasks information when a user selects a task *they have created*.
 * DIFFERENT from ViewTask as this implements an Edit button.
 *
 * @author Diane Boytang, Edited by Katherine Mae Patenio, Jonathan Ismail, Adrian Schuldhaus, Punam Woosaree
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
        setContentView(R.layout.activity_view_owntask);

        // Text layouts
        taskname = (TextView) this.findViewById(R.id.textview_taskname);
        taskstatus = (TextView) this.findViewById(R.id.viewown_status_textview);
        taskdescription = (TextView) this.findViewById(R.id.requirements_owntask_text);
        tasklocation = (TextView) this.findViewById(R.id.viewown_loc_textview);

        /* TOOL BAR GO BACK TO MAIN ACTIVITY */
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
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("user_name", userName);
                intent.putExtra("user_id", userID);
                //Log.i("AddActivity","Sending name and id to MainActivity!");
                startActivity(intent);
            }
        });

        /* REMOVE TOOLBAR SAVE BUTTON FOR THIS ACTIVITY */
        toolBarSaveBtn = (ImageButton)findViewById(R.id.toolbar_save_btn);
        toolBarSaveBtn.setVisibility(View.GONE);

        /* SET TITLE OF TOOLBAR */
        toolBarTitle = (TextView)findViewById(R.id.toolbar_title);
        toolBarTitle.setText("Your Task");


        // Display lowest bid
        tasklowestbidusername = (TextView) this.findViewById(R.id.lowestbid_textview_name);
        tasklowestbidamount = (TextView) this.findViewById(R.id.lowestbid_textview_price);


        // EDIT BUTTON
        Button editButton = (Button) findViewById(R.id.edit_btn);

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
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                String name = taskname.getText().toString();
                String description = taskdescription.getText().toString();
                String status = taskstatus.getText().toString();

                // Pass relevant information to EditActivity via SaveFileController
                final Context context = getApplicationContext();
                final SaveFileController saveFileController = new SaveFileController();
                final int userIndex = saveFileController.getUserIndex(context, userName);

                // id of task
                id = task.getId();

                // Send task info // TODO: refactor using just one task to pass? or is this better perfomance?
                String index = Integer.toString(userIndex);
                intent.putExtra("name", name);
                intent.putExtra("des", description);
                intent.putExtra("status", status);
                intent.putExtra("userindex", index);
                intent.putExtra("task_id", id);

                // Send user info
                intent.putExtra("userid", userID);
                intent.putExtra("userName", userName);

                startActivity(intent);
            }
        });


        //Location Details
        ImageButton locationButton = (ImageButton) findViewById(R.id.info_btn);

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


        tasklowestbidusername.setOnClickListener(new View.OnClickListener() {
            /**
             * PROFILE Textview
             * When task requester username is selected, their profile shows up
             @author Punam Woosaree

             */
            @Override
            public void onClick(View view) {

                String bidUsername = tasklowestbidusername.getText().toString();
                if (!bidUsername.equals("No bids yet!")) {
                    User bidUser = ServerWrapper.getUserFromUsername(bidUsername);

                    //Sending info to ViewOtherProfile
                    Intent intent = new Intent(ViewOwnTask.this, ViewOtherProfile.class);
                    intent.putExtra("user_name", bidUser.getUsername());
                    intent.putExtra("user_id", bidUser.getId());
                    Log.i("ViewTask", "Sending name and id to ViewOtherProfile!");
                    startActivity(intent);
                }
            }

        });

        // ACCEPT LOWEST BID BUTTON
        RelativeLayout acceptButton = (RelativeLayout) findViewById(R.id.newaccept_button);

        acceptButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Accepts the current lowest bid for the task.
             *
             * @author Adrian Schuldhaus
             *
             * @param view - instance of View
             */
            @Override
            public void onClick(View view) {

                if (!MainActivity.isOnline()){
                    Toast.makeText(getApplicationContext(), "You must be online to accept bids", Toast.LENGTH_SHORT).show();
                    return;
                }

                // If bids exist, accept lowest bid
                if ((task.getBids() != null) && (!task.getBids().isEmpty())) {

                    if (!task.getStatus().equals("BIDDED")){
                        Toast.makeText(getApplicationContext(), "You can no longer accept bids on this task", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    ArrayList<Bid> bidList = task.getBids();
                    Bid lowestBid = task.findLowestBid();

                    String CurrentBidderId = lowestBid.getUserId();
                    task.setStatus("ASSIGNED");
                    task.setAssignedId(CurrentBidderId);
                    User bidder = ServerWrapper.getUserFromId(CurrentBidderId);

                    // Display toast to tell user that they deleted a bid
                    String biddername = bidder.getUsername();
                    Toast.makeText(getApplicationContext(), "You have accepted "+ biddername +"\'s " + String.format("$%.2f", lowestBid.getAmount()) + " bid!",Toast.LENGTH_LONG).show();

                /* DELETE BIDS FROM TASK */
                /* UPDATE BIDDERS */
                    for (int i = 0; i < bidList.size(); i++){
                        Bid b = bidList.get(i);
                        User u = ServerWrapper.getUserFromId(b.getUserId());
                        u.removeBid(task);
                        ServerWrapper.updateUser(u);
                        bidList.remove(i);
                    }
                    bidList.add(lowestBid);
                    task.setBids(bidList);

                    ServerWrapper.updateJob(task);

                    Intent i = new Intent(ViewOwnTask.this, MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // for notification conditions
                    i.putExtra("user_name", userName);
                    i.putExtra("user_id", userID);
                    startActivity(i);
                }else{ // no bids - cannot accept
                    Toast.makeText(getApplicationContext(), "There are currently no bids on this task", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // OTHER BIDS BUTTON
        /**
         * Displays all bids for the current task, especially the lowest bid.
         * Selecting this button will lead the user to ViewOtherBids activity, where the user
         * can accept ONE bid or decline multiple bids.
         *
         * @param view - instance of View
         */
        RelativeLayout otherbidsButton = (RelativeLayout) findViewById(R.id.newother_button);
        otherbidsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /* EXCEPTION HANDLING */
                // Offline - cannot accept bids
                if (!MainActivity.isOnline()){
                    Toast.makeText(getApplicationContext(), "You must be online to accept bids", Toast.LENGTH_SHORT).show();
                    return;
                }

                /* RETURN TO VIEWOWNTASK - after accepting a bid */
                if ((task.getBids() != null) && (!task.getBids().isEmpty())) {

                    // Cannot accept bid - done or assigned status
                    if (!task.getStatus().equals("BIDDED")){
                        Toast.makeText(getApplicationContext(), "You can no longer accept bids on this task", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    id = task.getId();
                    String name = taskname.getText().toString();

                    Intent intent = new Intent(ViewOwnTask.this, ViewOtherBids.class);
                    intent.putExtra("name", name);
                    intent.putExtra("username", userName);
                    intent.putExtra("taskid", id);
                    startActivity(intent);
                }

                // No bids selected - notify user
                else if ((task.getBids() == null) || (task.getBids().isEmpty())){
                    Toast.makeText(ViewOwnTask.this, "Your task currently has no bids!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Display task information. Also receive user information from MainActivity.
     *
     */
    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent(); // receive task
        task = (Task) intent.getSerializableExtra("task");
        userID = intent.getStringExtra("userid");
        userName = intent.getStringExtra("userName");

        // Read and display task info
        taskname.setText(task.getName());
        taskstatus.setText(task.getStatus());
        tasklocation.setText(task.getLocation());
        taskdescription.setText(task.getDescription());

        tasklowestbid = task.findLowestBid();

        if (task.hasNewBids()){
            task.setHasNewBids(false);
            ServerWrapper.updateJob(task);
        }

        // Get bidder information
        try { // ensure bidder or a bid exists
            User bidder;
            if (MainActivity.isOnline()) { // online
                tasklowestbid = task.findLowestBid();
                bidder = ServerWrapper.getUserFromId(tasklowestbid.getUserId());
            } else { // offline
                SaveFileController saveFileController = new SaveFileController();
                Context context = getApplicationContext();
                bidder = saveFileController.getUserFromUserId(context, tasklowestbid.getUserId());
            }

            String userBidderName = bidder.getUsername();
            tasklowestbidusername.setText(userBidderName);

        }
        catch (Exception e){
            Log.e("VIEWOWNTASK","CATCH HAS BEEN CALLED");
            e.printStackTrace();
        }

        // Display the lowest bid
        try {
            if (task.getBids().isEmpty()) {
                tasklowestbidamount.setText("No bids at the moment!");
            } else {

                tasklowestbidamount.setText(String.format("$%.2f", tasklowestbid.getAmount()));
            }
        } catch (Exception e) { // list of bids is null
            tasklowestbidamount.setText("No bids yet!");
        }
    }
}

