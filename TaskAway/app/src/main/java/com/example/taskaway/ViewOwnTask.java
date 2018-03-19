package com.example.taskaway;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

/**
 * Created by Diane on 2018-03-14.
 * Acts as activity that displays a tasks information when a user selects a task *they have created*.
 * DIFFERENT from ViewTask as this implements an Edit button
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
    String id;
    String userID;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_own_task);

        taskname = (TextView)this.findViewById(R.id.my_task_name);
        taskstatus = (TextView)this.findViewById(R.id.my_task_status);
        taskdescription = (TextView)this.findViewById(R.id.my_task_details);
        tasklocation = (TextView)this.findViewById(R.id.my_task_location);
        /* TODO: use with elasticsearch:
            userid = task.getCreatorId();
            user = ServerWrapper.getUserFromId(userid);
            lowest
         */

        tasklowestbidusername = (TextView)this.findViewById(R.id.lowest_bid_username);
        tasklowestbidamount = (TextView)this.findViewById(R.id.lowest_bid_amount);

        Button editButton = (Button) findViewById(R.id.edit_button);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), EditActivity.class);
//                String name = taskname.getText().toString();
//                String description = taskdescription.getText().toString();
//                String status = taskstatus.getText().toString();
//                intent.putExtra("one", name);
//                intent.putExtra("two", description);
//                intent.putExtra("three", status);
                final Context context = getApplicationContext();
                final SaveFileController saveFileController = new SaveFileController();
                final Integer userIndex = saveFileController.getUserIndex(context, userName);
                id = task.getId();
                intent.putExtra("task", userIndex);
                intent.putExtra("task_id", id);
                intent.putExtra("userid", userID);
                intent.putExtra("userName", userName);
                //intent.putExtra("")
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart(){
        super.onStart();
        // TODO: read from server
        Intent intent = getIntent(); // receive task
        task = (Task) intent.getSerializableExtra("task");
        userID = intent.getStringExtra("userid");
        Log.i("userID", userID);
        userName = intent.getStringExtra("userName");
        Log.i("username", userName);

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
                tasklowestbidamount.setText(String.valueOf(tasklowestbid.getAmount()));
            }
        }
        catch (Exception e){
            tasklowestbidamount.setText("No bids yet!");
        }
    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

}


