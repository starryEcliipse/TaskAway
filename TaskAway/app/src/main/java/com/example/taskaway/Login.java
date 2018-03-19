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

import java.util.concurrent.TimeUnit;
import java.util.regex.*;

/**
 * Acts as the login activity when user first opens app.
 *
 * @author
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
        Button registerButton = (Button) findViewById(R.id.registerButton);
        final TextView continueOffline = (TextView) findViewById(R.id.continueOfflineTextView);
        final EditText userNameEdit = (EditText)findViewById(R.id.editTextUsername);
        final EditText passwordEdit = (EditText)findViewById(R.id.editTextPassword);

        //login button clicked
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userName = userNameEdit.getText().toString();
                String password = passwordEdit.getText().toString();

                if(userName.length()<8){
                    userNameEdit.setError("Username must be 8 characters minimum");
                    return;
                }

                Pattern p = Pattern.compile("^[a-zA-Z]+$");//. represents single character
                Matcher m = p.matcher(userName);
                if (!m.matches()){
                    userNameEdit.setError("Username must consist only of letters");
                    return;
                }

                if(password.length()<1){
                    passwordEdit.setError("A password is required");
                    return;
                }

                User current_user = ServerWrapper.getUserFromUsername(userName);
                if (current_user == null) {
                    userNameEdit.setError("We could not find a user with that username");
                    continueOffline.setVisibility(View.VISIBLE);
                    return;
                }

                if (!current_user.validatePassword(password)){
                    passwordEdit.setError("Incorrect password");
                    return;
                }

                String current_ID = current_user.getId();

                final Context context = getApplicationContext();
                SaveFileController saveFileController = new SaveFileController();
                saveFileController.addNewUser(context, current_user);

                Intent intent = new Intent(Login.this, MainActivity.class);
                intent.putExtra("user_id", current_ID);
                intent.putExtra("user_name", userName);
                startActivity(intent);

            }
        });

        //register button clicked
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userName = userNameEdit.getText().toString();
                String password = passwordEdit.getText().toString();

                if(userName.length()<8){
                    userNameEdit.setError("Username must be 8 characters minimum");
                    return;
                }

                Pattern p = Pattern.compile("^[a-zA-Z]+$");//. represents single character
                Matcher m = p.matcher(userName);
                if (!m.matches()){
                    userNameEdit.setError("Username must consist only of letters");
                    return;
                }

                if(password.length()<1){
                    passwordEdit.setError("A password is required");
                    return;
                }

                if(ServerWrapper.getUserFromUsername(userName)!= null){
                    userNameEdit.setError("Username is already taken");
                    return;
                }

                User user = new User(userName, null, null);

                user.setPassword(password);

                ServerWrapper.addUser(user);

                User current_user = ServerWrapper.getUserFromUsername(userName);

                if (current_user == null){
                    Log.i("LOGIN", "User not yet on server. Will attempt to fetch again in 2 seconds");
                    try{
                        TimeUnit.SECONDS.sleep(2);
                    }catch(Exception e){
                        Log.i("LOGIN", "Something happened when trying to stop thread. Aborting.");
                        return;
                    }
                    current_user = ServerWrapper.getUserFromUsername(userName);
                    if (current_user == null) {
                        Log.i("LOGIN", "User still not on server. Aborting.");
                        continueOffline.setVisibility(View.VISIBLE);
                        return;
                    }
                }

                String current_ID = current_user.getId();

                final Context context = getApplicationContext();
                SaveFileController saveFileController = new SaveFileController();
                saveFileController.addNewUser(context, current_user);

                Intent intent = new Intent(Login.this, MainActivity.class);
                intent.putExtra("user_id", current_ID);
                intent.putExtra("user_name", userName);
                startActivity(intent);

            }
        });

        // If server is unavailable
        continueOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = userNameEdit.getText().toString();
                String password = passwordEdit.getText().toString();

                if(userName.length()<8){
                    userNameEdit.setError("Username must be 8 characters minimum");
                    return;
                }

                Pattern p = Pattern.compile("^[a-zA-Z]+$");//represents String consisting of alphabetic characters only
                Matcher m = p.matcher(userName);
                if (!m.matches()){
                    userNameEdit.setError("Username must consist only of letters");
                    return;
                }

                if(password.length()<1){
                    passwordEdit.setError("A password is required");
                    return;
                }

                //TODO configure this user object so it doesn't crash
                User current_user = new User(userName, null, null);
                current_user.setPassword(password);
                String current_ID = null;//TODO what should go here?

                final Context context = getApplicationContext();
                SaveFileController saveFileController = new SaveFileController();
                saveFileController.addNewUser(context, current_user);

                Intent intent = new Intent(Login.this, MainActivity.class);
                intent.putExtra("user_id", current_ID);
                intent.putExtra("user_name", userName);
                startActivity(intent);
            }
        });

    }

    /**
     * Call superclass onStart
     *
     * @see AppCompatActivity
     */
    @Override
    protected void onStart(){
        super.onStart();

    }


    /**
     * Call superclass onDestroy
     *
     * @see AppCompatActivity
     */
    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
}
