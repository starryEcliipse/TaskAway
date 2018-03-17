package com.example.taskaway;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {

    private EditText nameField;
    private EditText requirementField;
    private EditText statusField;
    private Button cancelButton;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        nameField = (EditText) findViewById(R.id.editText2);
        requirementField = (EditText) findViewById(R.id.editText3);
        statusField = (EditText) findViewById(R.id.editText);

        saveButton = (Button) findViewById(R.id.button2);

        cancelButton = (Button) findViewById(R.id.button);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameField.getText().toString();
                if (name.isEmpty()) {
                    nameField.setError("Enter name");
                    return;
                }

                if (name.length() > 30){
                    nameField.setError("Name too long");
                    return;
                }

                String comment = requirementField.getText().toString();
                if (comment.isEmpty()){
                    requirementField.setError("Enter requirement");
                    return;
                }

                if (comment.length()>300) {
                    requirementField.setError("Description too long");
                    return;
                }

                String s = statusField.getText().toString();
                if (s.isEmpty()){
                    statusField.setError("Assign status");
                    return;
                }

                //TESTING

                Task task = new Task(name, comment, s, null, null, null, null);
                Context context = getApplicationContext();
                SaveFileController saveFileController = new SaveFileController();
                int index = saveFileController.getUserIndex(context, "someonee");
                saveFileController.addRequiredTask(context, index, task);

//
//                Intent intent = new Intent();
//                String userName = "someonee";
//                intent.putExtra("one", userName);
//                startActivity(intent);


            }
        });
    }




}
