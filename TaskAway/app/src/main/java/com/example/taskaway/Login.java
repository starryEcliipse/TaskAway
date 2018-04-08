package com.example.taskaway;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;
import java.util.regex.*;

/**
 * Acts as the login activity when user first opens app.
 *
 * @author
 */
public class Login extends AppCompatActivity {

    private RelativeLayout loadingCircle;

    /**
     * Creates and initializes login button and EditText of login page.
     * @param savedInstanceState - previously saved state of app
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_login);

        RelativeLayout loginButton = (RelativeLayout) findViewById(R.id.newloginButton);
        Button registerButton = (Button) findViewById(R.id.edit_btn);
        final TextView continueOffline = (TextView) findViewById(R.id.newcontinueOfflineTextView);
        final EditText userNameEdit = (EditText)findViewById(R.id.neweditTextUsername);
        final EditText passwordEdit = (EditText)findViewById(R.id.neweditTextPassword);

        loadingCircle = (RelativeLayout) findViewById(R.id.loadingCircle);


        //login button clicked
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadingCircle.setVisibility(View.VISIBLE);

                String userName = userNameEdit.getText().toString();
                String password = passwordEdit.getText().toString();

                if(userName.length()<8){
                    userNameEdit.setError("Username must be 8 characters minimum");
                    loadingCircle.setVisibility(View.GONE);
                    return;
                }

                Pattern p = Pattern.compile("^[a-zA-Z]+$");//. represents single character
                Matcher m = p.matcher(userName);
                if (!m.matches()){
                    userNameEdit.setError("Username must consist only of letters");
                    loadingCircle.setVisibility(View.GONE);
                    return;
                }

                if(password.length()<1){
                    passwordEdit.setError("A password is required");
                    loadingCircle.setVisibility(View.GONE);
                    return;
                }

                User current_user = ServerWrapper.getUserFromUsername(userName);
                if (current_user == null) {
                    userNameEdit.setError("We could not find a user with that username");
                    continueOffline.setVisibility(View.VISIBLE);
                    loadingCircle.setVisibility(View.GONE);
                    return;
                }

                Log.i("lOGIN", "current_user is NOT null->"+current_user.getUsername());

                if (!current_user.validatePassword(password)){
                    passwordEdit.setError("Incorrect password");
                    loadingCircle.setVisibility(View.GONE);
                    return;
                }

                final Context context = getApplicationContext();
                SaveFileController saveFileController = new SaveFileController();
                saveFileController.addNewUser(context, current_user);

                Intent intent = new Intent(Login.this, MainActivity.class);
                intent.putExtra("user_id", current_user.getId());
                intent.putExtra("user_name", current_user.getUsername());
                intent.putExtra("online_mode", "ONLINE");
                startActivity(intent);

            }
        });

        //register button clicked
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadingCircle.setVisibility(View.VISIBLE);

                String userName = userNameEdit.getText().toString();
                String password = passwordEdit.getText().toString();

                if(userName.length()<8){
                    userNameEdit.setError("Username must be 8 characters minimum");
                    loadingCircle.setVisibility(View.GONE);
                    return;
                }

                Pattern p = Pattern.compile("^[a-zA-Z]+$");//. represents single character
                Matcher m = p.matcher(userName);
                if (!m.matches()){
                    userNameEdit.setError("Username must consist only of letters");
                    loadingCircle.setVisibility(View.GONE);
                    return;
                }

                if(password.length()<1){
                    passwordEdit.setError("A password is required");
                    loadingCircle.setVisibility(View.GONE);
                    return;
                }

                if(ServerWrapper.getUserFromUsername(userName)!= null){
                    Log.i("LOGIN","Testing register FAILED-->"+ServerWrapper.getUserFromUsername(userName).getUsername());
                    userNameEdit.setError("Username is already taken");
                    loadingCircle.setVisibility(View.GONE);
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
                        loadingCircle.setVisibility(View.GONE);
                        return;
                    }
                    current_user = ServerWrapper.getUserFromUsername(userName);
                    if (current_user == null) {
                        Log.i("LOGIN", "User still not on server. Aborting.");
                        loadingCircle.setVisibility(View.GONE);
                        return;
                    }
                }

                final Context context = getApplicationContext();
                SaveFileController saveFileController = new SaveFileController();
                saveFileController.addNewUser(context, current_user);

                Intent intent = new Intent(Login.this, MainActivity.class);
                intent.putExtra("user_id", current_user.getId());
                intent.putExtra("user_name", current_user.getUsername());
                intent.putExtra("online_mode", "ONLINE");
                startActivity(intent);

            }
        });

        // If server is unavailable
        continueOffline.setOnClickListener(new View.OnClickListener() {
            /**
             * Allows the user to continue using the app offline if the server is unavailable.
             *
             * @param v - instance of View
             *
             * @see SaveFileController
             */
            @Override
            public void onClick(View v) {

                loadingCircle.setVisibility(View.VISIBLE);

                String userName = userNameEdit.getText().toString();
                String password = passwordEdit.getText().toString();

                if(userName.length()<8){
                    userNameEdit.setError("Username must be 8 characters minimum");
                    loadingCircle.setVisibility(View.GONE);
                    return;
                }

                Pattern p = Pattern.compile("^[a-zA-Z]+$");//represents String consisting of alphabetic characters only
                Matcher m = p.matcher(userName);
                if (!m.matches()){
                    userNameEdit.setError("Username must consist only of letters");
                    loadingCircle.setVisibility(View.GONE);
                    return;
                }

                if(password.length()<1){
                    passwordEdit.setError("A password is required");
                    loadingCircle.setVisibility(View.GONE);
                    return;
                }

                User current_user = new User(userName, null, null);
                current_user.setPassword(password);
                String current_ID = null;

                final Context context = getApplicationContext();
                SaveFileController saveFileController = new SaveFileController();
                saveFileController.addNewUser(context, current_user);

                // Go to Main Activity
                Intent intent = new Intent(Login.this, MainActivity.class);
                intent.putExtra("user_id", current_ID);
                intent.putExtra("user_name", userName);
                intent.putExtra("online_mode", "OFFLINE");
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
