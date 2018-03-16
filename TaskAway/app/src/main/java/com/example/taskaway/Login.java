package com.example.taskaway;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Acts as the login activity when user first opens app.
 */
public class Login extends AppCompatActivity {

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

                if(userName.length()<8){
                    userNameEdit.setError("Username must be 8 characters minimum");
                    return;
                }

                User user = new User(userName, null, null);

                /**
                 * DO NOT REMOVE COMMENTS
                 * Those lines are for when we have server connectivity
                 */
                //ServerWrapper.addUser(user);
                //User current_user = ServerWrapper.getUserFromUsername(userName);
                //String current_ID = current_user.getId();

                final Context context = getApplicationContext();
                SaveFileController saveFileController = new SaveFileController();
                saveFileController.addNewUser(context, user);

                Intent intent = new Intent(Login.this, MainActivity.class);
                //intent.putExtra("user_id", current_ID);
                intent.putExtra("user_name", userName);
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
