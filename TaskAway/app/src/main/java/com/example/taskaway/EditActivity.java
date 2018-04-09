
package com.example.taskaway;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

/**
 *
 * This class handles editing / deleting an existing task.
 *
 * @see Task
 * @see ViewOwnTask
 * @author Sameerah Wajahat
 *
 */
public class EditActivity extends AppCompatActivity {

    private EditText tname;
    private EditText des;
    private EditText location;


    private ImageButton toolBarSaveBtn;
    private ImageButton toolBarBackbtn;
    private ImageView imageV;

    private Task task;
    private ImageButton uploadPic;
    private String new_name;
    private String new_des;
    private String new_location;
    ArrayList<String> pictures = new ArrayList<String>();
    ArrayList<byte[]> arrayB = new ArrayList<byte[]>();
    String userName;
    String user_id;
    String task_id;
    String creator_id;
    String assigned_id;
    int index;


    /**
     * Upon creating activity, create EditText and Button layouts.
     * Also retrieve username and user id information for obtaining appropriate task information.
     * Determine behaviours of buttons.
     *
     * @param savedInstanceState - saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        // Get information needed to update task
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        Log.i("EDITACT",""+userName);
        user_id = intent.getStringExtra("userid");
        //userName = intent.getStringExtra("userName");
        Log.i("EDITACT",""+user_id);
        task_id = intent.getStringExtra("task_id");
        Log.i("EDITACT",""+task_id);
        Log.i("EDITACT",""+ServerWrapper.getJobFromId(task_id).getName());
        Log.i("EDITACT",""+ServerWrapper.getJobFromId(task_id).getPictures());

        // EditText layout
        tname = (EditText) findViewById(R.id.name_edit_text);
        des = (EditText) findViewById(R.id.viewtask_requirementsk);
        //status = (EditText) findViewById(R.id.editText);
        imageV = (ImageView) findViewById(R.id.imageView_edit);
        location = (EditText) findViewById(R.id.location_edit_text);

        uploadPic = (ImageButton) findViewById(R.id.image_camera_edit_btn);
        uploadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pictures = task.getPictures();
                if (pictures != null) {
                    Intent intent = new Intent(EditActivity.this, UploadPicEdit.class);
                    intent.putExtra("userid", user_id);
                    intent.putExtra("username", userName);
                    intent.putExtra("task_id", task_id);

                    //intent.putExtra("task",task);
                    for (int n = 0; n < pictures.size(); n++) {
                        byte[] encodeByte = Base64.decode(pictures.get(n), Base64.DEFAULT);
                        //Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                        arrayB.add(encodeByte);
                    }
                    intent.putExtra("byteArraySize", arrayB.size());
                    for (int i = 0; i < arrayB.size(); i++) {
                        intent.putExtra("barray"+i, arrayB.get(i));
                        Log.i("UPLOAD", "barray(i)"+arrayB.get(i));
                    }
                    startActivity(intent);
                }

                else{
                    Log.i("EDITACTIVITYNOPICTURES", "NO PICTURES");
                    return;
                }
            }
        });

        int size = intent.getIntExtra("byteArraySize", 0);
        Log.i("RECEIVE SIZE", "size: "+ size);
        if (intent.getByteArrayExtra("barray0") != null){
            ArrayList<byte[]> barray = new ArrayList<>();
            //int size = intent.getIntExtra("byteArraySize", 0);
            for (int i = 0; i < size; i++) {
                barray.add(intent.getByteArrayExtra("barray"+i));
                Log.i("RECIEVED", "barray(i)"+barray.get(i));
            }

            byte b[] = barray.get(0);
            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            Log.i("BITMAPREC", "BITMAP"+bitmap);

            imageV.measure(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            int width = imageV.getMeasuredWidth();
            int height = imageV.getMeasuredHeight();
            bitmap = getResizedBitmap(bitmap, height, width);

            imageV.setImageBitmap(bitmap);

            for (int n = 0; n < size; n++){
                String temp= Base64.encodeToString(barray.get(n), Base64.DEFAULT);
                pictures.add(temp);
                //arrayB.add(temp);
            }


        }
//        //else if (intent.getByteArrayExtra("bytearray") == null){
//        else if (intent.getByteArrayExtra("barry0") == null){
//            Log.i("ADDTASK"," null!");
//        }

        if (MainActivity.isOnline()){
            task = ServerWrapper.getJobFromId(task_id);

            //Task is a ghost somehow. Must be exterminated
            if (task == null){
                User u = ServerWrapper.getUserFromId(user_id);
                u.removeTask(new Task(null, null, null, null, null, null, null , task_id));
                ServerWrapper.updateUser(u);
                Toast.makeText(getApplicationContext(), "This task should not still exist", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(EditActivity.this, MainActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent2.putExtra("user_name", userName);
                intent2.putExtra("user_id", user_id);
                startActivity(intent2);
                return;
            }
        }else {
            // Get userindex
            final String i = intent.getStringExtra("userindex");
            Log.i("EditActivity", "Received string User Index is " + i);
            index = Integer.parseInt(i);
            Log.i("EditActivity", "User index as int is " + index);

            // SaveFileController - getTask
            final Context context1 = getApplicationContext();
            SaveFileController saveFileController1 = new SaveFileController();
            task = saveFileController1.getTask(context1, index, task_id);
            Log.i("USER INDEX", index + "");
            Log.i("TASK ID:", task_id + "");
        }

        // Get task information
        new_name = task.getName();
        new_des = task.getDescription();
        //new_status = task.getStatus();
        new_location = task.getLocation();

        creator_id = task.getCreatorId();
        assigned_id = task.getAssignedId();

        // setText
        tname.setText(new_name);
        des.setText(new_des);
        location.setText(new_location);


        // Cancel button - cancel activity
        toolBarBackbtn = (ImageButton)findViewById(R.id.toolbar_back_btn);
        toolBarBackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(EditActivity.this, ViewOwnTask.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent2.putExtra("userName", userName);
                intent2.putExtra("userid", user_id);
                intent2.putExtra("task",task);
                startActivity(intent2);
            }
        });



        // Save button - save changes and update task
        toolBarSaveBtn = (ImageButton)findViewById(R.id.toolbar_save_btn);
        toolBarSaveBtn.setOnClickListener(new View.OnClickListener() {
            /**
             * Determines behaviour of save button.
             * Reads inputted text fields (if valid) and updates task information
             *
             * @see SaveFileController
             */
            @Override
            public void onClick(View view) {



                if (task.getStatus().equals("REQUESTED")) {


                    // Task name
                    String name = tname.getText().toString();
                    if (name.isEmpty()) {
                        tname.setError("Enter name");
                        return;
                    }
                    if (name.length() > 30) {
                        tname.setError("Name too long");
                        return;
                    }

                    // Description
                    String comment = des.getText().toString();
                    if (comment.isEmpty()) {
                        des.setError("Enter requirement");
                        return;
                    }
                    if (comment.length() > 300) {
                        des.setError("Description too long");
                        return;
                    }
                    String s = "REQUESTED"; // default value

                    // Task location
                    String loc = location.getText().toString();
                    if (loc.isEmpty()) {
                        loc = "N/A";
                    }

                    ArrayList<Bid> bidlist = task.getBids();

                    // TODO: remove null
                    Task task = new Task(name, comment, s, loc, bidlist, pictures, null, task_id);
                    task.setCreatorId(creator_id);
                    task.setAssignedId(assigned_id);

                    if (MainActivity.isOnline()){
                        task.updateCoordinates();
                        ServerWrapper.updateJob(task);
                    }else{
                        // SAVE TO FILE
                        final Context context = getApplicationContext();
                        SaveFileController saveFileController = new SaveFileController();
                        int userindex = saveFileController.getUserIndex(context, userName); // get userindex

                        if (task.getSyncInstruction().equals("OFFLINE_ADD")){
                            // do not change the instruction, since it has yet to be added to the server
                        }else{
                            task.setSyncInstruction("OFFLINE_UPDATE"); // change instruction to signify it needs to be updated on next login
                        }
                        // Update user information
                        saveFileController.updateTask(context, userindex, task_id, task); // add task to proper user in savefile
                    }

                    // GO TO MAIN ACTIVITY
                    Intent intent2 = new Intent(EditActivity.this, MainActivity.class);
                    intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent2.putExtra("user_name", userName);
                    intent2.putExtra("user_id", user_id);
                    //Log.i("AddActivity", "Sending name and id to MainActivity!");
                    startActivity(intent2);
                } // end of if
                else if (!task.getStatus().equals("REQUESTED")){
                    Toast.makeText(EditActivity.this, "Unable to save changes: this task has already received bids!", Toast.LENGTH_SHORT).show();
                }
            } // end of onclick
        });

        // Delete button - deletes the task being viewed
        RelativeLayout delete = (RelativeLayout) findViewById(R.id.DeleteButton);
        delete.setOnClickListener(new View.OnClickListener() {
            /**
             * Determines delete button behaviour.
             * Deletes the current task. Updates user information.
             *
             * @see SaveFileController
             */
            @Override
            public void onClick(View view) {

                if (MainActivity.isOnline()){
                    ServerWrapper.deleteJob(task);
                    Log.i("NOTICEME!", "Attempting to delete task");
                }else{
                    final Context context2 = getApplicationContext();
                    SaveFileController saveFileController2 = new SaveFileController();
                    task.markDeleted();
                    if (task.getSyncInstruction().equals("OFFLINE_ADD")){
                        // Can just delete it locally since it has yet to be added to the server
                        // Update user information - remove task
                        saveFileController2.deleteTask(context2, index, task_id); // add task to proper user in savefile
                        saveFileController2.deleteTaskBids(context2, index, -1, task_id); // update for task providers - delete task
                    }else{
                        task.setSyncInstruction("OFFLINE_DELETE");
                        // Update user information - remove task
                        saveFileController2.updateTask(context2, index, task_id, task); // updates task to proper user in savefile
                    }
                }

                // GO TO MAIN ACTIVITY
                Intent intent2 = new Intent(EditActivity.this, MainActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent2.putExtra("user_name", userName);
                intent2.putExtra("user_id", user_id);
                //Log.i("AddActivity","Sending name and id to MainActivity!");
                startActivity(intent2);
            }
        });
    } // end of onCreate

    /**
     * Calls superclass onStart.
     *
     * @see AppCompatActivity
     */
    @Override
    protected void onStart(){
        super.onStart();
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {

        int width = bm.getWidth();

        int height = bm.getHeight();

        float scaleWidth = ((float) newWidth) / width;

        float scaleHeight = ((float) newHeight) / height;

// create a matrix for the manipulation

        Matrix matrix = new Matrix();

// resize the bit map

        matrix.postScale(scaleWidth, scaleHeight);

// recreate the new Bitmap

        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);

        return resizedBitmap;

    }
}
