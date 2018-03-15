package com.example.taskaway;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Diane on 2018-03-14.
 * Acts as activity that displays a tasks information when a user selects a task *they have created*.
 * DIFFERENT from ViewTask as this implements an Edit button
 */

public class ViewOwnTask extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_own_task);
        Button editButton = (Button) findViewById(R.id.editbutton);

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
    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

}
