package com.example.taskaway;

import android.content.Context;
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
import java.util.Collections;


/**
 * Displays bids that have been placed on the current task.
 * User can accept ONE bid or delete other bids.
 *
 * @author Jonathan Ismail
 * Created on 2018-04-01
 *
 * @see Bid
 * @see MyBids
 * @see OtherBidsViewAdapter
 * //@see activity_view_other_bids.xml
 * //@see item_otherbids.xml
 */
public class ViewOtherBids extends AppCompatActivity {

    View rootView;
    private TextView tname;
    private RecyclerView myrecyclerview;
    private RecyclerView.Adapter adapter;
    //private static TaskList lstTask;
    private String user_name;
    private String user_id;

    //private ArrayList<Task> lstTask;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_other_bids);

        tname = (TextView) findViewById(R.id.textView_otherbids);
        Intent intent = getIntent();
        String taskName = intent.getStringExtra("name"); // task name
        tname.setTextSize(48);
        SpannableString content = new SpannableString(taskName);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tname.setText(content);


        myrecyclerview = (RecyclerView) findViewById(R.id.otherbids_recyclerview);
        myrecyclerview.setHasFixedSize(true);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(this));

        user_name = intent.getStringExtra("username");
        String taskid = intent.getStringExtra("taskid");


        /*
        lstTask = new ArrayList<>();
        lstTask.add(new Task("AAAA",null,null,null,null,null,null,null));
        lstTask.add(new Task("AAAA",null,null,null,null,null,null,null));
        lstTask.add(new Task("AAAA",null,null,null,null,null,null,null));
        lstTask.add(new Task("AAAA",null,null,null,null,null,null,null));
        lstTask.add(new Task("AAAA",null,null,null,null,null,null,null));
        lstTask.add(new Task("AAAA",null,null,null,null,null,null,null));
        lstTask.add(new Task("AAAA",null,null,null,null,null,null,null));
        lstTask.add(new Task("AAAA",null,null,null,null,null,null,null));
        lstTask.add(new Task("AAAA",null,null,null,null,null,null,null)); */

        // TODO: just pass the task instead?

        SaveFileController saveFileController = new SaveFileController();
        Context context = getApplicationContext();
        final int userindex = saveFileController.getUserIndex(context, user_name);
        Task task = saveFileController.getTask(context, userindex, taskid);
        ArrayList<Bid> bidList = task.getBids();
        // Get and display all other bids that are not the lowest
        // assume size > 1
        ArrayList<Bid> bidListOther = new ArrayList<Bid>(bidList);
        Bid bid = task.findLowestBid();
        bidListOther.remove(bid);
        adapter = new OtherBidsViewAdapter(this, bidListOther);
        
        //adapter = new OtherBidsViewAdapter(this,lstTask);
        myrecyclerview.setAdapter(adapter);


    }

}

