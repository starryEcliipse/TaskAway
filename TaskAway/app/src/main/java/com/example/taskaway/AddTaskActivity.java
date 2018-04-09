package com.example.taskaway;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * This activity is responsible to adding new tasks for the current user.
 * @author Sameerah Wajahat, Edited by Jonathan Ismail, Katherine Mae Patenio
 *
 * @see EditActivity
 * @see SaveFileController
 */
public class AddTaskActivity extends AppCompatActivity {
    private EditText nameField;
    private EditText requirementField;
    private EditText locationField;
    private ImageButton toolBarSaveBtn;
    private ImageButton uploadPic;
    private ImageButton toolBarBackbtn;
    private ImageView imageSet;
    ArrayList<String> arrayB = new ArrayList<String>();
    private String username;
    private String userid;
    private RelativeLayout rl;

    /**
     *
     * Creates EditText fields so that user can input information for a new task.
     * Creates buttons as well.
     * Also determines button behaviours when clicked on.
     *
     * @param savedInstanceState - previously saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        // EditText layouts
        imageSet = (ImageView) findViewById(R.id.imageView_edit);
        nameField = (EditText) findViewById(R.id.name_edit_text);
        requirementField = (EditText) findViewById(R.id.assigned_requirementsk);
        locationField = (EditText) findViewById(R.id.location_edit_text);
        uploadPic = (ImageButton) findViewById(R.id.image_camera_btn);
        rl = (RelativeLayout) findViewById(R.id.relative_lay);


        toolBarBackbtn = (ImageButton)findViewById(R.id.toolbar_back_btn);
        toolBarBackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddTaskActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("user_name", username);
                intent.putExtra("user_id", userid);
                Log.i("AddActivity","name is "+username+" and id is "+userid);
                startActivity(intent);
            }
        });

        uploadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent0 = new Intent(getBaseContext(), UploadPic.class);
                intent0.putExtra("username", username);
                intent0.putExtra("userid",userid);
                startActivity(intent0);
            }
        });

        toolBarSaveBtn = (ImageButton)findViewById(R.id.toolbar_save_btn);
        toolBarSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameField.getText().toString();

                // Check task name input
                if (name.isEmpty()) {
                    nameField.setError("Enter name");
                    return;
                }
                if (name.length() > 30){
                    nameField.setError("Name too long");
                    return;
                }
                String comment = requirementField.getText().toString();

                // Check task description input
                if (comment.isEmpty()){
                    requirementField.setError("Enter requirement");
                    return;
                }
                if (comment.length()>300) {
                    requirementField.setError("Description too long");
                    return;
                }
                String s = "REQUESTED";

                String location = locationField.getText().toString();
                // Check location input
                if(location.isEmpty()){
                    location = "N/A";
                }


                // NEW TASK - create with valid input
                String task_id = Calendar.getInstance().getTime().toString();
                task_id = task_id.replaceAll(" ", "");
                Task task = new Task(name, comment, s, location, null, arrayB, null, task_id);

                // Include current user's ID into the task so that we can identify to whom the task
                // belongs to later on
                task.setCreatorId(userid);

                if (MainActivity.isOnline()){
                    task.updateCoordinates();
                    ServerWrapper.addJob(task);
                    Log.i("AddTaskActivity", "Adding Task to server");
                }else{
                    task.setSyncInstruction("OFFLINE_ADD");
                    // SAVE TO FILE
                    final Context context = getApplicationContext();
                    SaveFileController saveFileController = new SaveFileController();
                    int userindex = saveFileController.getUserIndex(context, username); // get userindex
                    //Log.i("AddActivity","userindex is "+userindex);
                    saveFileController.addRequiredTask(context, userindex, task); // add task to proper user in savefile
                }

                // GO TO MAIN ACTIVITY
                Intent intent2 = new Intent(AddTaskActivity.this, MainActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent2.putExtra("user_name", username);
                intent2.putExtra("user_id", userid);
                //Log.i("AddActivity","Sending name and id to MainActivity!");
                startActivity(intent2);

            } // end of onClick
        });

    } // end of onCreate()

    // SOURCE: https://stackoverflow.com/questions/4989182/converting-java-bitmap-to-byte-array
    public static Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onStart(){
        super.onStart();
        // READ USERNAME AND USER ID FROM MAIN
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        userid = intent.getStringExtra("userid");

        //THIS IS WHERE WE HAVE TO RECEIVE THE BYTE ARRAYSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS
        //if (intent.getStringArrayListExtra("images") != null) {
        int size = intent.getIntExtra("byteArraySize", 0);
        Log.i("RECEIVE SIZE", "size: "+ size);
        if (intent.getByteArrayExtra("barray0") != null){
            ArrayList<byte[]> barray = new ArrayList<>();
            //int size = intent.getIntExtra("byteArraySize", 0);
            for (int i = 0; i < size; i++) {
                barray.add(intent.getByteArrayExtra("barray"+i));
                Log.i("RECIEVED", "barray(i)"+barray.get(i));
            }
            //byte b[] = getIntent().getByteArrayExtra("bytearray");

            byte b[] = barray.get(0);
            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);

            for (int n = 0; n < size; n++){
                String temp= Base64.encodeToString(barray.get(n), Base64.DEFAULT);
                arrayB.add(temp);
            }

            // SOURCE:
            imageSet.measure(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            int width = imageSet.getMeasuredWidth();
            int height = imageSet.getMeasuredHeight();
            bitmap = getResizedBitmap(bitmap, height, width);

            imageSet.setImageBitmap(bitmap);

        }
        else if (intent.getByteArrayExtra("bytearray") == null){
            Log.i("ADDTASK"," null!");
        }

    }

    // Makes sure image fits imageview
    // SOURCE: https://thinkandroid.wordpress.com/2009/12/25/resizing-a-bitmap/
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

