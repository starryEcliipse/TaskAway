package com.example.taskaway;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.lang.reflect.Array;
import java.net.URI;
import java.util.ArrayList;

public class UploadPic extends AppCompatActivity implements View.OnClickListener{
    private static final int RESULT_LOAD_IMAGE = 1;

    ArrayList<Uri> arrayU = new ArrayList<Uri>();
    ArrayList<Bitmap> arrayN = new ArrayList<Bitmap>();
    Button cancel;
    Button upload;
    Button done;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pic);
        upload = (Button) findViewById(R.id.button5);
        cancel = (Button) findViewById(R.id.button8);
        done = (Button) findViewById(R.id.button7);

        cancel.setOnClickListener(this);
        done.setOnClickListener(this);
        upload.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.button7:
                Intent in = new Intent(this, AddActivity.class);
                in.putParcelableArrayListExtra("bitmap", arrayN);
                startActivity(in);
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

    //https://stackoverflow.com/questions/23426113/how-to-select-multiple-images-from-gallery-in-android

    //https://stackoverflow.com/questions/13511356/android-image-selected-from-gallery-orientation-is-always-0-exif-tag
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){

            // Ensures that we don't accidentally append to the array of images if the user changes mind
            // TODO: make new instance of array for better performance BIG O wise?
            if (!arrayU.isEmpty()){
                arrayU.clear();
            }

            // If single photo, the photo will NOT be in ClipData - just return it
            // SOURCE: https://stackoverflow.com/a/40475323
            if (data.getClipData() == null){
                Uri uri = data.getData();
                arrayU.add(uri);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                try {
                    BitmapFactory.decodeStream(getContentResolver().openInputStream(arrayU.get(0)), null, options);
                    options.inJustDecodeBounds = false;
                    Bitmap image = BitmapFactory.decodeStream(getContentResolver().openInputStream(arrayU.get(0)), null, options);
                    String type = getContentResolver().getType(arrayU.get(0));
                    Log.i("STUPID TYPE", type);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    if (type.contains("png")) {
                        image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    } else if (type.contains("jpg") || type.contains("jpeg")) {
                        image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    }
                    arrayN.add(image);
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
                        BitmapFactory.decodeStream(getContentResolver().openInputStream(arrayU.get(j)), null, options);
                        options.inJustDecodeBounds = false;
                        Bitmap image = BitmapFactory.decodeStream(getContentResolver().openInputStream(arrayU.get(j)), null, options);
                        String type = getContentResolver().getType(arrayU.get(j));
                        Log.i("STUPID TYPE", type);
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        if (type.contains("png")) {
                            image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        } else if (type.contains("jpg") || type.contains("jpeg")) {
                            image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                        }
                        arrayN.add(image);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }

            GridView gridview = (GridView) findViewById(R.id.gridview);
            gridview.setAdapter(new PicturesImageAdapter(UploadPic.this, arrayN));


        }
    }



}
