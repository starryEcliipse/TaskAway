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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import java.util.ArrayList;

public class PhotosViewOwnTask extends AppCompatActivity {

    ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();
    //ArrayList<byte[]> barray = new ArrayList<byte[]>();
    private String userID;
    private String user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos_view_own_task);

        Intent intent = getIntent();
        user_name = intent.getStringExtra("username");
        userID = intent.getStringExtra("userid");

        //THIS IS WHERE WE HAVE TO RECEIVE THE BYTE ARRAYSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS
        //if (intent.getStringArrayListExtra("images") != null) {
        int size = intent.getIntExtra("byteArraySize", 0);
        Log.i("RECEIVE SIZE", "size: "+ size);
        if (intent.getByteArrayExtra("barray0") != null) {
            //ArrayList<byte[]> barray = new ArrayList<>();
            //int size = intent.getIntExtra("byteArraySize", 0);
            for (int i = 0; i < size; i++) {
                byte b[] = intent.getByteArrayExtra("barray" + i);
                Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
                bitmaps.add(bitmap);
                Log.i("RECIEVED", "one bitmap" + bitmap);
            }

            //byte b[] = getIntent().getByteArrayExtra("bytearray");
        }
        GridView gridview = (GridView) findViewById(R.id.gridView);
        gridview.setAdapter(new PicturesImageAdapter(PhotosViewOwnTask.this, bitmaps));
    }
}
