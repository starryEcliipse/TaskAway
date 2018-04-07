package com.example.taskaway;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.sql.Array;
import java.util.ArrayList;

/**
 * Acts as activity that displays a tasks information when a user selects a task
 * that they have previously bid on.
 *
 * @author Diane Boytang
 * Created on 2018-03-16
 */

public class ViewBidTask extends AppCompatActivity {

    private TextView taskname;
    private TextView taskdescription;
    private TextView tasklocation;
    private TextView taskwinningbid;
    private TextView useroldbid;
    private TextView taskusername;
    private EditText userbid;
    private Task task;
    String id;
    String userName;
    String userID;

    /**
     * Create layout and buttons. Determine button behaviours.
     * @param savedInstanceState - saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bid_task);

        // Text layouts
        taskname = (TextView) this.findViewById(R.id.task_bid_name);
        taskdescription = (TextView) this.findViewById(R.id.task_bid_details);
        tasklocation = (TextView) this.findViewById(R.id.task_bid_location);
        taskwinningbid = (TextView) this.findViewById(R.id.winning_bid_amount_2);
        useroldbid = (TextView) this.findViewById(R.id.old_price_amount);
        userbid = (EditText) this.findViewById(R.id.new_bid_amount);
        taskusername = (TextView) this.findViewById(R.id.task_user_name);

        //Location Details
        Button locationButton = (Button) findViewById(R.id.location_detail_button);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get task information
                Intent intent = new Intent(getBaseContext(), MapActivity.class);
                String name = taskname.getText().toString();
                String location = tasklocation.getText().toString();
                if (location.equals("N/A")) {
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

        // CANCEL button - cancel activity
        Button cancelButton = (Button) findViewById(R.id.cancel_button_2);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Lets user stop viewing bid on a task.
             *
             * @param view - instance of View
             */
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        /* PROFILE Textview
        @author Punam Woosaree
        When task requester username is selected, their profile shows up
         */
        TextView requestUser = (TextView) findViewById(R.id.task_user_name);
        requestUser.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                User requesterUser = ServerWrapper.getUserFromId(task.getCreatorId());

                //Sending info to ViewOtherProfile
                Intent intent = new Intent(ViewBidTask.this, ViewOtherProfile.class);
                intent.putExtra("user_name", requesterUser.getUsername());
                intent.putExtra("user_id", requesterUser.getId());
                Log.i("ViewTask", "Sending name and id to ViewOtherProfile!");
                startActivity(intent);
            }

        });




        // SAVE BUTTON
        Button saveButton = (Button) findViewById(R.id.save_button_2);
        saveButton.setOnClickListener(new View.OnClickListener() {

            /**
             * Updates the task provider's current bid on a task when the save button is selected
             * Updates list of bids for a certain task for task requester.
             * Updates list of tasks task provider has bidded on
             *
             * @author Katherine Mae Patenio
             *
             * @param view - instance of View
             *
             * @see SaveFileController
             */
            @Override
            public void onClick(View view) {

                String inputbid = userbid.getText().toString();
                float bidamount;

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

                Bid bid = new Bid(userID, bidamount);

                ArrayList<Bid> bidList = task.getBids();

                /* CHECK IF BID IN BIDLIST ALREADY CAUSE NOT DECLINED  */
                boolean bidExists = false; // default assume task exists

                for (int i = 0; i < bidList.size(); i++) {
                    if (bidList.get(i).getUserId().equals(userID)) {
                        bidList.set(i, bid); // updating bid if exists
                        bidExists = true;
                        break;
                    }
                } // end of for

                if (!bidExists){
                    bidList.add(bid);
                }

                task.setBids(bidList);

                if (MainActivity.isOnline()){
                    ServerWrapper.updateJob(task);
                    Log.i("ViewBidTask", "Updating Task on server");
                    User u = ServerWrapper.getUserFromId(userID);
                    if (u != null){
                        u.addBid(task);
                        ServerWrapper.updateUser(u);
                        Log.i("ViewBidTask", "Updating User on server");
                    }
                }

                /*// SAVEFILECONTROLLER FOR UPDATING THE TASK'S LIST OF BIDS
                final Context context = getApplicationContext();
                SaveFileController saveFileController = new SaveFileController();

                // get userindex of the task requester
                int userindexCreator = saveFileController.getUserIndexFromCreatorID(context, task.getCreatorId());
                saveFileController.updateTask(context, userindexCreator, task.getId(), task);


                // SAVEFILECONTROLLER FOR UPDATING MYBIDS MENU

                // A task the user has bid on should now appear in the middle menu
                int userindexBidder = saveFileController.getUserIndex(context, userName);

                // Update bids on tasks that other users bidded on
                saveFileController.updateTaskBids(context, userindexCreator, task, task.getId(), bid);*/

                // GO BACK TO MAIN
                Intent intent2 = new Intent(ViewBidTask.this, MainActivity.class);
                intent2.putExtra("user_name", userName);
                intent2.putExtra("user_id", userID);
                Log.i("ViewBidTask", "Sending name and id to MainActivity!");
                startActivity(intent2);

            } // end of onClick override
        }); // end of onclicklistener
    } // end of method

    /**
     * Displays Task information.
     *
     * @see Task
     */
    @Override
    protected void onStart(){
        super.onStart();

        Intent intent = getIntent();
        task = (Task) intent.getSerializableExtra("task");
        // read and display task info
        taskname.setText(task.getName());
        tasklocation.setText(task.getLocation());
        taskdescription.setText(task.getDescription());

        User requester;
        String requestername;
        if (MainActivity.isOnline()){
            requester = ServerWrapper.getUserFromId(task.getCreatorId());
            if (requester != null){
                requestername = requester.getUsername();
            }else{
                requestername = "UNKNOWN";
                Toast.makeText(getApplicationContext(), "An error occurred while trying to get task creator's username", Toast.LENGTH_SHORT).show();
            }
        }else{
            SaveFileController saveFileController = new SaveFileController();
            requester = saveFileController.getUserFromUserId(getApplicationContext(), task.getCreatorId());
            requestername = requester.getUsername();
        }

        taskusername.setText(requestername);

        // Get current user information
        userID = intent.getStringExtra("userid");
        Log.i("ViewBidTask", "userid is: "+userID);
        userName = intent.getStringExtra("userName");
        Log.i("ViewBidTask", "username is: "+userName);

        // GET CURRENT TASK PROVIDER'S PREVIOUS RECENT BID
        float recentbid = 0;
        ArrayList<Bid> bidList;

        bidList = task.getBids();

        for (int i=0; i < bidList.size(); i++){
            if (bidList.get(i).getUserId().equals(userID)){
                recentbid = bidList.get(i).getAmount();
                useroldbid.setText(String.format("$%.2f", recentbid));
                break;
            }
        }

        Bid winningbid;

        try {
            if (task.getBids().isEmpty()) {
                taskwinningbid.setText("No bids yet!");
            } else {
                winningbid = task.findLowestBid();
                taskwinningbid.setText(String.format("$%.2f", winningbid.getAmount()));
                Log.i("ViewTask", "On start");
                // Just for reading all bids in IDE Console - for debugging purposes
                for (Bid temp : task.getBids()) {
                    Log.i("ViewTask", "Reading: " + temp.getAmount()+ " by "+temp.getUserId());
                }
            }
        }
        catch (Exception e){
            taskwinningbid.setText("No bids yet!");
        }

    }

    /**
     * Call superclass onDestroy.
     *
     * @see AppCompatActivity
     */
    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
}
