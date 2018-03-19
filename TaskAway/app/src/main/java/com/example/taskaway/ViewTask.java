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

/**
 * Acts as activity that displays a tasks information when a user selects a task
 * that they HAVE NOT previously bid on.
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);

        taskname = (TextView)this.findViewById(R.id.task_name);
        taskdescription = (TextView)this.findViewById(R.id.task_details);
        taskstatus = (TextView)this.findViewById(R.id.task_status);
        tasklocation = (TextView)this.findViewById(R.id.task_location);
        taskwinningbid = (TextView)this.findViewById(R.id.winning_bid_amount);
        userbid = (EditText)this.findViewById(R.id.your_bid_amount);

        // TODO: ElasticSearch

        Button cancelButton = (Button) findViewById(R.id.cancel_button_1);
        Button saveButton = (Button) findViewById(R.id.save_button_1);

        // Save bid
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // read input
                String inputbid = userbid.getText().toString();
                try{
                    if (inputbid.isEmpty()){
                        userbid.setError("No bid entered!");
                        return;
                    }
                    bidamount = Float.parseFloat(inputbid);
                    // Invalid input
                } catch (Exception e){
                    Log.i("ViewTask","Invalid bid entered!");
                    userbid.setError("Invalid bid entered!");
                    return;
                }
                // Valid input
                Log.i("ViewTask","The new bid is: "+bidamount);


                // TODO: make bid objects?
                //Bid bid = new Bid(userID, bidamount);
                //task.getBids(bid);

                // SaveFileController
                final Context context = getApplicationContext();
                SaveFileController saveFileController = new SaveFileController();
                int userindex = saveFileController.getUserIndex(context, userName); // get userindex
                Log.i("ViewTask","userindex is "+userindex);
                Log.i("ViewTask","Adding "+task.getName()+" task!");
                saveFileController.addBiddedTask(context, userindex, task);


                // GO BACK TO MAIN
                Intent intent2 = new Intent(ViewTask.this, MainActivity.class);
                intent2.putExtra("user_name", userName);
                intent2.putExtra("user_id", userID);
                Log.i("ViewTwask","Sending name and id to MainActivity!");
                startActivity(intent2);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.i("ViewTask","Viewing task!");
        //TODO: Read from server

        // TODO: update to use SaveFileController?
        Intent intent = getIntent();
        task = (Task) intent.getSerializableExtra("task");
        // read and display task info
        taskname.setText(task.getName());
        taskstatus.setText(task.getStatus());
        tasklocation.setText(task.getLocation());
        taskdescription.setText(task.getDescription());

        userID = intent.getStringExtra("userid");
        Log.i("userID", userID);
        userName = intent.getStringExtra("userName");
        Log.i("userName", userName);

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

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
}
