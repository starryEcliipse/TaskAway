package com.example.taskaway;

import android.content.Context;
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
 * Created by SJIsmail .
 *  * The following below creates a layout class for the All Bids class, working progress... Will be similar to Jobs class

 */

public class AllBids extends Fragment {
    View rootView;
    private RecyclerView myrecyclerview;
    //private ArrayList<Task> lstTask;
    private static TaskList lstTask;
    private String user_name;
    private String user_id;


    public AllBids() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.allbids_layout, container, false);

        myrecyclerview = (RecyclerView) rootView.findViewById(R.id.allbids_recyclerview);

        AllBidsListViewAdapter recycleAdapter = new AllBidsListViewAdapter(getContext(), lstTask);

        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        myrecyclerview.setAdapter(recycleAdapter);

        return rootView;

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Task(String name, String description, String status, String location, ArrayList<Bid> bids, ArrayList<Task> pictures, Float lowestBid)
        //nTask = new Task("Cleaner Joe","Cleaning","",null,null,null,null);

        // GET USERNAME AND ID FROM LOGIN - TO BE PASSED TO SOME ACTIVITIES
        if (getArguments() != null){
            Log.i("All Bids","getArguments NOT null!");
            //Bundle bundle = new Bundle();
            user_name = getArguments().getString("username");
            Log.i("All Bids",getArguments().getString("username")+"");
            user_id = getArguments().getString("userid");
            Log.i("All Bids", getArguments().getString("userid")+"");
        }

        final Context context = getContext();
        SaveFileController saveFileController = new SaveFileController();
        int userIndex = saveFileController.getUserIndex(context, user_name);
        Log.i("All Bids","Currently getting all other tasks");
        lstTask = saveFileController.getAllTasks(context);

        //TESTING BELOW
        //lstTask = new ArrayList<>();
        //lstTask.add(new Task("Job 7","This is a test!","Requested","Edmonton, AB",null,null,null, null));
        //lstTask.add(new Task("Job 8",null,null,null,null,null,null,null));
        //lstTask.add(new Task("Job 9",null,null,null,null,null,null, null));


    }
}
