package com.example.taskaway;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class TaskUserRequests extends AppCompatActivity {
    private ArrayList<Task> reqTask;
    private ArrayAdapter<Task> adapter;
    private ListView requestTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestTask = (ListView) findViewById(R.id.requestTask);
        setContentView(R.layout.activity_task_user_requests);
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

