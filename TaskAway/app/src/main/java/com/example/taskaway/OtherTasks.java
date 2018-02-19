package com.example.taskaway;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Acts as activity that displays all tasks that a user can bid on.
 */
public class OtherTasks extends AppCompatActivity {

    private ArrayList<Task> otherTask;
    private ArrayAdapter<Task> adapter;
    private ListView othersTask;

    /**
     * Creates and initializes ListView upon creating app.
     * @param savedInstanceState - previously saved state of app
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        othersTask = (ListView) findViewById(R.id.otherTask);
        setContentView(R.layout.activity_other_tasks);
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
