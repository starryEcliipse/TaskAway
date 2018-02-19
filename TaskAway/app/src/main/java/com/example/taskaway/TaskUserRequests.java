package com.example.taskaway;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Acts as activity that displays all tasks a user has requested/created.
 */
public class TaskUserRequests extends AppCompatActivity {
    private ArrayList<Task> reqTask;
    private ArrayAdapter<Task> adapter;
    private ListView requestTaskList; // updated variable name - kpatenio 18/02/18

    /**
     * Creates and intializes ListView upon creating app.
     * @param savedInstanceState - previously saved state of app
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestTaskList = (ListView) findViewById(R.id.requestTask);
        setContentView(R.layout.activity_task_user_requests);
        // TODO: Buttons - 18/02/18
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

