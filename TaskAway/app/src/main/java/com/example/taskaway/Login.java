package com.example.taskaway;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

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
        // TODO: Create login button 18/02/18
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
