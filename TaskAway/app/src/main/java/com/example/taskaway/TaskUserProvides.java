package com.example.taskaway;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Acts as activity that displays all tasks a user has bid on.
 */
public class TaskUserProvides extends AppCompatActivity {
    private ArrayList<Task> proTask;
    private TaskList tasklist;
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

        // upon clicking on a task in ListView, go to viewtask activity
        provideTaskList.setOnItemClickListener(new ListView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> a, View v, int position, long row){
                setResult(RESULT_OK);
                Intent intent = new Intent(TaskUserProvides.this, ViewTask.class);
                startActivity(intent);
            }
        }); // end of setOnItemClickListener
    } // end of onCreate

    @Override
    protected void onStart(){
        super.onStart();
        // TODO: clarify what mybids layout is
        // TODO: what to do with MyBids class?
        // TODO: update - frags for "sections" in our interface
       // adapter = new ArrayAdapter<Task>(TaskUserProvides.this, R.layout.mybids_layout, R.id.mybids_listview, tasklist);
       // provideTaskList.setAdapter(adapter);
    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
}
