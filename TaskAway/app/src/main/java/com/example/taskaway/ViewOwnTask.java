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
    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_own_task);

        taskname = (TextView)this.findViewById(R.id.my_task_name);

        Button editButton = (Button) findViewById(R.id.edit_button);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: set intent to switch to the EditTask activity
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
    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

}


