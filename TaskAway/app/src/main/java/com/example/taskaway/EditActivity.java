package com.example.taskaway;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {
    private EditText tname;
    private EditText des;
    private EditText aprice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        tname = (EditText) findViewById(R.id.editText2);
        des = (EditText) findViewById(R.id.editText3);

        Intent intent = getIntent();
        String name = intent.getStringExtra("one");
        String description = intent.getStringExtra("two");
        tname.setText(name);
        des.setText(description);
    }
}
