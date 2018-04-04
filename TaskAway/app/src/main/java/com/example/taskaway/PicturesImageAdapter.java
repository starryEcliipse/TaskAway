package com.example.taskaway;

/**
 * Created by KatherineMae on 4/3/2018.
 */

import android.content.Context;

import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class PicturesImageAdapter extends BaseAdapter {
    private Context mContext;

    // Constructor
    public PicturesImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(300, 300));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(3, 3, 3, 3);
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    // Keep all Images in array
    public Integer[] mThumbIds = {
            // TODO: find a way to display images here - currently using drawable images
            /*
                LINKS
                https://www.tutorialspoint.com/android/android_grid_view.htm
                https://developer.android.com/guide/topics/ui/layout/gridview.html#example
                Maybe useful?: https://dzone.com/articles/download-and-display-image-in-android-gridview

             */

            // TEST STUFF ONLY - delete cat photo later
            R.drawable.cat, R.drawable.cat,
            R.drawable.cat, R.drawable.cat,
            R.drawable.cat, R.drawable.cat,

           /* R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7 */

    };
}