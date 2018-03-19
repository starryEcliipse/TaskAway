package com.example.taskaway;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SJIsmail.
 * The following below creates a layout class for the Bids class, working progress... Will be similar to Jobs class
 */

public class MyBids extends Fragment{
    View rootView;
    private RecyclerView myrecyclerview;
    private ArrayList<Task> lstTask;

    public MyBids(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.mybids_layout, container, false);

        myrecyclerview = (RecyclerView) rootView.findViewById(R.id.bids_recyclerview);

        MyBidsListViewAdapter recycleAdapter = new MyBidsListViewAdapter(getContext(), lstTask);

        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        myrecyclerview.setAdapter(recycleAdapter);


        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Task(String name, String description, String status, String location, ArrayList<Bid> bids, ArrayList<Task> pictures, Float lowestBid)
        //nTask = new Task("Cleaner Joe","Cleaning","",null,null,null,null);

        //TESTING BELOW
        lstTask = new ArrayList<>();
        lstTask.add(new Task("Job 4","This is a Test!","Requested","Calgary, AB",null,null,null));
        lstTask.add(new Task("Job 5",null,null,null,null,null,null));
        lstTask.add(new Task("Job 6",null,null,null,null,null,null));


    }
}
