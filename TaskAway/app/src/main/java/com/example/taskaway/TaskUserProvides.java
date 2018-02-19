package com.example.taskaway;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Acts as activity that displays all tasks a user has bid on.
 */
public class TaskUserProvides extends AppCompatActivity {

    private ArrayList<Task> proTask;
    private ArrayAdapter<Task> adapter;
    private ListView provideTaskList;

    /**
     * Creates and initializes ListView upon creating app.
     * @param savedInstanceState - previously saved state of app
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        provideTaskList = (ListView) findViewById(R.id.provideTask);
        setContentView(R.layout.activity_task_user_provides);
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
