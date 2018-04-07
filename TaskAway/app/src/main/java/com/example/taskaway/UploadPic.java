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

    //private Context mContext;
    LinearLayout linearMain;
    //ImageView imageToUpload;
    ArrayList<Uri> arrayU = new ArrayList<Uri>();
    //Uri selectedImage;
    //ArrayList<String> imagesPathList;
    //Bitmap bitmap;
    Button upload;
    Button done;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pic);
        //linearMain = (LinearLayout) findViewById(R.id.linearImage);
        //GridView gridview = (GridView) findViewById(R.id.gridview);
        //gridview.setAdapter(new PicturesImageAdapter(UploadPic.this, arrayU));
        //imageToUpload = (ImageView) findViewById(R.id.imageToUpload);
        upload = (Button) findViewById(R.id.button5);
        done = (Button) findViewById(R.id.button7);

        done.setOnClickListener(this);
        upload.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.button7:
                //image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

                break;

            // upload button
            case R.id.button5:
                //https://stackoverflow.com/questions/23426113/how-to-select-multiple-images-from-gallery-in-android
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                break;
        }
    }

    //https://stackoverflow.com/questions/13511356/android-image-selected-from-gallery-orientation-is-always-0-exif-tag
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            //ArrayList<Uri> arrayU = null;
            //Uri selectedImage = null;


            Log.i("UPLOAD PIC","IS EMPTY: "+arrayU.isEmpty());

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
            }

            // Else, use ClipData
            else {
                for (int i = 0; i < data.getClipData().getItemCount(); i++) {
                    // check if same URI
                    Uri uri = data.getClipData().getItemAt(i).getUri();
                    arrayU.add(uri);
                }
            }

            //Uri selectedImage = data.getClipData().getItemAt(1).getUri();

//            for (int j = 0; j < arrayU.size(); j++) {
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inJustDecodeBounds = true;
//                try {
//                    BitmapFactory.decodeStream(getContentResolver().openInputStream(arrayU.get(j)), null, options);
//                    options.inJustDecodeBounds = false;
//                    Bitmap image = BitmapFactory.decodeStream(getContentResolver().openInputStream(arrayU.get(j)), null, options);
//                    String type = getContentResolver().getType(arrayU.get(j));
//                    Log.i("STUPID TYPE", type);
//                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                    if (type.contains("png")) {
//                        image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
//                    } else if (type.contains("jpg") || type.contains("jpeg")) {
//                        image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
//                    }
//                    imageToUpload.setImageBitmap(image);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//
//            }
            //Uri selectedImage = data.getClipData().getItemAt(0).getUri();
            //imageToUpload.setImageURI(selectedImage);

            //ClipData mClipData = data.getClipData();
            //GridView gridview = (GridView) findViewById(R.id.gridview);
            //gridview.removeAllViews();
/*            int pickedImageCount;
            for (pickedImageCount = 0; pickedImageCount < data.getClipData().getItemCount();
                 pickedImageCount++) {
                Uri selectedImage = data.getData();
                arrayU.add(selectedImage);
            }*/
            GridView gridview = (GridView) findViewById(R.id.gridview);
            gridview.setAdapter(new PicturesImageAdapter(UploadPic.this, arrayU));
            //Uri selectedImage = data.getData();
            //imageToUpload.setImageURI(selectedImage);
            //https://stackoverflow.com/questions/23426113/how-to-select-multiple-images-from-gallery-in-android

 /*           ClipData mClipData = data.getClipData();
            //linearMain.removeAllViews();
            int pickedImageCount;

            for (pickedImageCount = 0; pickedImageCount < mClipData.getItemCount();
                 pickedImageCount++) {

                ImageView productImageView =
                        new ImageView(getApplicationContext());

                productImageView.setAdjustViewBounds(true);

                productImageView.setScaleType(ImageView.ScaleType.FIT_XY);

                productImageView.setLayoutParams(new LinearLayout
                        .LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT));

                linearMain.addView(productImageView);
            }*/

            //Uri selectedImage = data.getClipData().getItemAt(0).getUri();

            /*Bitmap bitmap = null;
            //        Uri selectedImage1 = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }*/

            //imageToUpload.setImageBitmap(bitmap);

        }
    }



}
