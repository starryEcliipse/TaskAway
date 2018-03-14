package com.example.taskaway;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Acts as the login activity when user first opens app.
 */
public class Login extends AppCompatActivity {

    private EditText username;
    private EditText password;

    /**
     * Creates and initializes login button and EditText of login page.
     * @param savedInstanceState - previously saved state of app
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton = (Button) findViewById(R.id.loginButton);
        final EditText userNameEdit = (EditText)findViewById(R.id.editTextUsername);
        final EditText passwordEdit = (EditText)findViewById(R.id.editTextPassword);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userName = userNameEdit.getText().toString();
                String password = passwordEdit.getText().toString();

                User user = new User(userName, null, null, password, null, null ,null );

                TextView usernameTextView = (TextView)findViewById(R.id.editName);
                usernameTextView.setText(userName);

                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);

            }
        });

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
