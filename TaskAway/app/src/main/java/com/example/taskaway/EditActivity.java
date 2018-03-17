package com.example.taskaway;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        tname = (EditText) findViewById(R.id.editText2);
        des = (EditText) findViewById(R.id.editText3);
        status = (EditText) findViewById(R.id.editText);

        Intent intent = getIntent();
        String name = intent.getStringExtra("one");
        String description = intent.getStringExtra("two");
        String statusTask = intent.getStringExtra("three");
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

        Button delete = (Button) findViewById(R.id.button3);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



    }

    @Override
    protected void onStart(){
        super.onStart();
    }
}
