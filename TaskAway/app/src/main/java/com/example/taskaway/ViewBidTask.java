package com.example.taskaway;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Acts as activity that displays a tasks information when a user selects a task
 * that they HAVE previously bid on.
 *
 * WORK IN PROGRESS
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
    private EditText userbid;
    private Task task;
    private Bid winningbid;
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
        taskname = (TextView)this.findViewById(R.id.task_bid_name);
        taskdescription = (TextView)this.findViewById(R.id.task_bid_details);
        tasklocation = (TextView)this.findViewById(R.id.task_bid_location);
        taskwinningbid = (TextView)this.findViewById(R.id.winning_bid_amount_2);
        useroldbid = (TextView)this.findViewById(R.id.old_price_amount);
        userbid = (EditText)this.findViewById(R.id.new_bid_amount);

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


                // id of task
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

        // SAVE BUTTON
        // TODO - functionality of save button
        Button saveButton = (Button) findViewById(R.id.save_button_2);
    }


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
     * Call superclass onDestroy.
     *
     * @see AppCompatActivity
     */
    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
}
