package com.example.taskaway;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class UploadPic extends AppCompatActivity implements View.OnClickListener{
    private static final int RESULT_LOAD_IMAGE = 1;

    ImageView imageToUpload;
    Button upload;
    Button done;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pic);
        imageToUpload = (ImageView) findViewById(R.id.imageToUpload);
        upload = (Button) findViewById(R.id.button5);
        done = (Button) findViewById(R.id.button7);

        done.setOnClickListener(this);
        upload.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.button7:
                
                break;

            case R.id.button5:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            imageToUpload.setImageURI(selectedImage);
        }
    }
}
