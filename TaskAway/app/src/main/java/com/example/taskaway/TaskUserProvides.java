package com.example.taskaway;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class TaskUserProvides extends AppCompatActivity {

    private ArrayList<Task> proTask;
    private ArrayAdapter<Task> adapter;
    private ListView provideTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        provideTask = (ListView) findViewById(R.id.provideTask);
        setContentView(R.layout.activity_task_user_provides);
    }

    @Override
    protected void OnStart(){

    }


    @Override
    protected void OnDestroy(){

    }
}
