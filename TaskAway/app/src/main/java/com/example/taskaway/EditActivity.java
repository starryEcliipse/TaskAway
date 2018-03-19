package com.example.taskaway;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Sameerah Wajahat
 * This class handles editing / deleting an existing task
 *
 */

public class EditActivity extends AppCompatActivity {
    private EditText tname;
    private EditText des;
    private EditText status;
    private Button cancel;
    private Button delete;
    private Button save;
    private Task task;
    String userName;
    String user_id;
    String task_id;
    Integer index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        tname = (EditText) findViewById(R.id.editText2);
        des = (EditText) findViewById(R.id.editText3);
        status = (EditText) findViewById(R.id.editText);

        Intent intent = getIntent();

//        String name = intent.getStringExtra("one");
//        String description = intent.getStringExtra("two");
//        final String statusTask = intent.getStringExtra("three");

        userName = intent.getStringExtra("user_name");
        user_id = intent.getStringExtra("userId");
        task_id = intent.getStringExtra("task_id");
        index = intent.getStringExtra("index");

        tname.setText(name);
        des.setText(description);
        status.setText(statusTask);

        cancel = (Button) findViewById(R.id.button);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        save = (Button) findViewById(R.id.button2);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = tname.getText().toString();
                if (name.isEmpty()) {
                    tname.setError("Enter name");
                    return;
                }

                if (name.length() > 30){
                    tname.setError("Name too long");
                    return;
                }

                String comment = des.getText().toString();
                if (comment.isEmpty()){
                    des.setError("Enter requirement");
                    return;
                }

                if (comment.length()>300) {
                    des.setError("Description too long");
                    return;
                }

                String s = status.getText().toString();
                if (s.isEmpty()){
                    status.setError("Assign status");
                    return;
                }

                Task task = new Task(name, comment, s, null, null, null, null, );

                // SAVE TO FILE TODO: ELASTICSEARCH
                final Context context = getApplicationContext();
                SaveFileController saveFileController = new SaveFileController();
                int userindex = saveFileController.getUserIndex(context, userName); // get userindex
                Log.i("AddActivity","userindex is "+userindex);
                saveFileController.updateTask(context, userindex, , task); // add task to proper user in savefile


                // GO TO MAIN ACTIVITY
                Intent intent2 = new Intent(EditActivity.this, MainActivity.class);
                intent2.putExtra("user_name", userName);
                intent2.putExtra("user_id", user_id);
                Log.i("AddActivity","Sending name and id to MainActivity!");
                startActivity(intent2);
            }
        });

        Button delete = (Button) findViewById(R.id.button3);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //needs to be worked on once,
                //taskList.remove(position);
            }
        });



    }

    @Override
    protected void onStart(){
        super.onStart();
    }
}
