package com.example.taskaway;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

public class PicturesDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictures_display);

        // Create grid view and set adapter
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new PicturesImageAdapter(PicturesDisplay.this));
    }
}
