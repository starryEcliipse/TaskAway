package com.example.taskaway;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Diane on 2018-03-16.
 * Acts as activity that displays a tasks information when a user selects a task
 * that they HAVE previously bid on.
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bid_task);

        taskname = (TextView)this.findViewById(R.id.task_bid_name);
        taskdescription = (TextView)this.findViewById(R.id.task_bid_details);
        tasklocation = (TextView)this.findViewById(R.id.task_bid_location);
        taskwinningbid = (TextView)this.findViewById(R.id.winning_bid_amount_2);
        useroldbid = (TextView)this.findViewById(R.id.old_price_amount);
        userbid = (EditText)this.findViewById(R.id.new_bid_amount);

        Button cancelButton = (Button) findViewById(R.id.cancel_button_2);
        Button saveButton = (Button) findViewById(R.id.save_button_2);

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



    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
}
