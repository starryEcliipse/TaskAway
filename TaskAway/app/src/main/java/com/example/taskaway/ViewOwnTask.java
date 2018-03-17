package com.example.taskaway;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
                String name = taskname.getText().toString();
                String description = taskdescription.getText().toString();
                intent.putExtra("one", name);
                intent.putExtra("two", description);
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
        taskname.setText(task.getName()); // read task name and display it
        taskstatus.setText(task.getStatus());
        tasklocation.setText(task.getLocation());
        taskdescription.setText(task.getDescription());
    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

}


