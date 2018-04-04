package com.example.taskaway;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created on 2018-04-01 - SJIsmail
 */
public class ViewOtherBids extends AppCompatActivity {

    View rootView;
    private TextView tname;
    //private RecyclerView myrecyclerview;
    //private ArrayList<Task> lstTask;
    private RecyclerView myrecyclerview;
    private RecyclerView.Adapter adapter;
    private String user_name;
    private String user_id;
    String userName;

    private ArrayList<Task> lstTask;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_other_bids);

        tname = (TextView) findViewById(R.id.textView_otherbids);
        Intent intent = getIntent();
        userName = intent.getStringExtra("name");
        tname.setTextSize(48);
        SpannableString content = new SpannableString(userName);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tname.setText(content);


        myrecyclerview = (RecyclerView) findViewById(R.id.otherbids_recyclerview);
        myrecyclerview.setHasFixedSize(true);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(this));

        lstTask = new ArrayList<>();
        lstTask.add(new Task("AAAA",null,null,null,null,null,null,null));
        lstTask.add(new Task("AAAA",null,null,null,null,null,null,null));
        lstTask.add(new Task("AAAA",null,null,null,null,null,null,null));
        lstTask.add(new Task("AAAA",null,null,null,null,null,null,null));
        lstTask.add(new Task("AAAA",null,null,null,null,null,null,null));
        lstTask.add(new Task("AAAA",null,null,null,null,null,null,null));
        lstTask.add(new Task("AAAA",null,null,null,null,null,null,null));
        lstTask.add(new Task("AAAA",null,null,null,null,null,null,null));
        lstTask.add(new Task("AAAA",null,null,null,null,null,null,null));

        adapter = new OtherBidsViewAdapter(this,lstTask);
        myrecyclerview.setAdapter(adapter);


    }

}

