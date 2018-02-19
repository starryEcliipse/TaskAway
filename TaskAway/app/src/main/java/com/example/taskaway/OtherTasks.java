package com.example.taskaway;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class OtherTasks extends AppCompatActivity {

    private ArrayList<Task> otherTask;
    private ArrayAdapter<Task> adapter;
    private ListView othersTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        othersTask = (ListView) findViewById(R.id.otherTask);
        setContentView(R.layout.activity_other_tasks);
    }

    @Override
    protected void OnStart(){

    }

    @Override
    protected void OnDestroy(){

    }
}
