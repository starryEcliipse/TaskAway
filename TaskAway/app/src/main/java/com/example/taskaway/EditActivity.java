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
    private TaskList taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        tname = (EditText) findViewById(R.id.editText2);
        des = (EditText) findViewById(R.id.editText3);
        status = (EditText) findViewById(R.id.editText);

        final Intent intent = getIntent();
        String name = intent.getStringExtra("one");
        String description = intent.getStringExtra("two");
        final String statusTask = intent.getStringExtra("three");
        final String position = intent.getStringExtra("position");
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
                Intent intent2 = new Intent(getBaseContext(), ViewOwnTask.class);
                String name = tname.getText().toString();
                String description = des.getText().toString();
                String s = status.getText().toString();
                intent2.putExtra("one", name);
                intent2.putExtra("two", description);
                intent2.putExtra("three", s);
                intent2.putExtra("four", position);
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
