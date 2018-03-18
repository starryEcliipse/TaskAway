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
    private TaskList taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        tname = (EditText) findViewById(R.id.editText2);
        des = (EditText) findViewById(R.id.editText3);
        status = (EditText) findViewById(R.id.editText);

        final String user_name = getIntent().getStringExtra("user_name");
        final String userID = getIntent().getStringExtra("user_id");

        final Intent intent = getIntent();
        String name = intent.getStringExtra("one");
        String description = intent.getStringExtra("two");
        final String statusTask = intent.getStringExtra("three");
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
