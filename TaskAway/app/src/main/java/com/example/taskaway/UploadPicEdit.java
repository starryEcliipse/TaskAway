/*
 * Copyright (c) 2018 Team X, CMPUT301. University of Alberta - All rights reserved.
 * You may use distribute and modify this code under terms and conditions of Code of Student Behavior at
 * University of Alberta
 * You can find a copy of this license in this project. Otherwise please contact contact@abc.ca
 * /
 */

package com.example.taskaway;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class UploadPicEdit extends AppCompatActivity implements View.OnClickListener {
    private static final int RESULT_LOAD_IMAGE = 1;

    ArrayList<Uri> arrayU = new ArrayList<Uri>();
    ArrayList<Bitmap> arrayN = new ArrayList<Bitmap>();
    ArrayList<String> arrayS = new ArrayList<String>();
    ArrayList<byte[]> arrayB = new ArrayList<byte[]>();
    Button cancel;
    Button upload;
    Button done;
    byte b[]; // byte array for image - sent to AddTask
    String username;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pic_edit);
        upload = (Button) findViewById(R.id.button5);
        cancel = (Button) findViewById(R.id.button8);
        done = (Button) findViewById(R.id.button7);

        cancel.setOnClickListener(this);
        done.setOnClickListener(this);
        upload.setOnClickListener(this);

        if (!arrayU.isEmpty()) {
            arrayU.clear();
        }
        if (!arrayN.isEmpty()) {
            arrayN.clear();
        }
        if (!arrayS.isEmpty()) {
            arrayS.clear();
        }

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        userid = intent.getStringExtra("userid");

        int size = intent.getIntExtra("byteArraySize", 0);
        Log.i("RECEIVE SIZE", "size: " + size);
        if (intent.getByteArrayExtra("barray0") != null) {
            //ArrayList<byte[]> barray = new ArrayList<>();
            //int size = intent.getIntExtra("byteArraySize", 0);
            for (int i = 0; i < size; i++) {
                byte b[] = intent.getByteArrayExtra("barray" + i);
                Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
                arrayN.add(bitmap);
                Log.i("RECIEVED", "one bitmap" + bitmap);
            }

            GridView gridview = (GridView) findViewById(R.id.gridview);
            gridview.setAdapter(new PicturesImageAdapter(UploadPicEdit.this, arrayN));
            //byte b[] = getIntent().getByteArrayExtra("bytearray");
        } else {
            return;
        }
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.button7:
                // THIS IS WHERE WE'RE LAUNCHING THE ADD ACTIVITY TO SEND THE PHOTOS TO BE ADDED TO THE TASK

                Intent in = new Intent(this, EditActivity.class);

                //Source: https://stackoverflow.com/questions/36521965/how-to-pass-byte-list-to-another-activity
                //arrayB.add(b);
                in.putExtra("byteArraySize", arrayB.size());
                for (int i = 0; i < arrayB.size(); i++) {
                    in.putExtra("barray"+i, arrayB.get(i));
                    Log.i("UPLOAD", "barray(i)"+arrayB.get(i));
                }
                in.putExtra("username", username);
                in.putExtra("userid",userid);
                startActivity(in);

                //in.putStringArrayListExtra("images", arrayS);
                //in.putParcelableArrayListExtra("images", arrayN);
                break;

            // upload button
            case R.id.button5:
                //https://stackoverflow.com/questions/23426113/how-to-select-multiple-images-from-gallery-in-android
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryIntent.setType("image/*");
                galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                break;

            case R.id.button8:
                finish();
        }
    }

    /**
     * Receives photos from gallery. The photos received are then sent to AddTaskActivity in bytes.
     * Ensures that the photos are less than 65536
     *
     * https://stackoverflow.com/questions/23426113/how-to-select-multiple-images-from-gallery-in-android
     * https://stackoverflow.com/questions/4989182/converting-java-bitmap-to-byte-array
     * https://stackoverflow.com/questions/13511356/android-image-selected-from-gallery-orientation-is-always-0-exif-tag
     *
     * @param requestCode - the request code
     * @param resultCode - the result code
     * @param data - will contain the Uri stuff of photos
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){

            // Ensures that we don't accidentally append to the array of images if the user changes mind
            if (!arrayU.isEmpty()){
                arrayU.clear();
            }
            if (!arrayN.isEmpty()){
                arrayN.clear();
            }
            if (!arrayS.isEmpty()){
                arrayS.clear();
            }
            // IN THE ELSE CONDITION ONE BYTE ARRAY IS BEING OVERWRITTEN IN THE FOR LOOP- HAVE TO FIGURE OUT HOW TO PASS
            // MULTIPLE PHOTOS BYTES ARRAYSSSSSSSS
            // If single photo, the photo will NOT be in ClipData - just return it
            // SOURCE: https://stackoverflow.com/a/40475323
            if (data.getClipData() == null){
                Uri uri = data.getData();
                arrayU.add(uri);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                try {
                    //options.inSampleSize = calculateInSampleSize(options, 125, 125 );
                    options.inJustDecodeBounds = false;
                    Bitmap image = BitmapFactory.decodeStream(getContentResolver().openInputStream(arrayU.get(0)), null, options);
                    String type = getContentResolver().getType(arrayU.get(0));
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    if (type.contains("png")) {
                        image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    } else if (type.contains("jpg") || type.contains("jpeg")) {
                        image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    }


                    // Image size is fine
                    arrayN.add(image);
                    b = byteArrayOutputStream.toByteArray();
                    // Check image size - ensure image is under 65.536 kb
                    if ((b.length/4) > 65536){
                        Toast.makeText(getApplicationContext(), "One of your images is too large!", Toast.LENGTH_LONG).show();
                        return;
                    }
                    String temp= Base64.encodeToString(b, Base64.DEFAULT);
                    arrayB.add(b);
                    arrayS.add(temp);


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            // Else, use ClipData
            else {
                for (int i = 0; i < data.getClipData().getItemCount(); i++) {
                    // check if same URI
                    Uri uri = data.getClipData().getItemAt(i).getUri();
                    arrayU.add(uri);
                }
                for (int j = 0; j < arrayU.size(); j++) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    try {
                        options.inJustDecodeBounds = false;
                        Bitmap image = BitmapFactory.decodeStream(getContentResolver().openInputStream(arrayU.get(j)), null, options);
                        String type = getContentResolver().getType(arrayU.get(j));
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        if (type.contains("png")) {
                            image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        } else if (type.contains("jpg") || type.contains("jpeg")) {
                            image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                        }

                        arrayN.add(image);
                        b = byteArrayOutputStream.toByteArray();
                        // Check image size - ensure image is under 65.536 kb
                        if ((b.length/4) > 65536){
                            Toast.makeText(getApplicationContext(), "One of your images is too large!", Toast.LENGTH_LONG).show();
                            return;
                        }
                        String temp= Base64.encodeToString(b, Base64.DEFAULT);
                        arrayS.add(temp);
                        arrayB.add(b);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }

            GridView gridview = (GridView) findViewById(R.id.gridview);
            gridview.setAdapter(new PicturesImageAdapter(UploadPicEdit.this, arrayN));


        }
    }

    @Override
    public void onStart(){
        super.onStart();
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        userid = intent.getStringExtra("userid");

    }


}


