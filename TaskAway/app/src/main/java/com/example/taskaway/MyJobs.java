package com.example.taskaway;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by SJIsmail.
 * The following below creates a layout class for the Jobs requested class
 *  https://developer.android.com/reference/android/support/v7/widget/RecyclerView.html
 *  https://www.youtube.com/watch?v=oBhgyiBVd3k <- More examples/explanation
 */

public class MyJobs extends Fragment {
    /**
     * This class will also create an array of tasks and display the arraylist in the "myjobs_layout"
     */
    View rootView;
    private RecyclerView myrecyclerview;
    private ArrayList<Task> lstTask;


    public MyJobs() {
    }
    /**
     * instantiate the ViewPagerAdapter class with the lstTask arraylist
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.myjobs_layout, container, false);
        myrecyclerview = (RecyclerView) rootView.findViewById(R.id.jobs_recyclerview);
        TaskListViewAdapter recycleAdapter = new TaskListViewAdapter(getContext(), lstTask);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        myrecyclerview.setAdapter(recycleAdapter);
        return rootView;

    }
    /**
     * Testing the functionality of the arraylist and its compatibility with the layout "
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Task(String name, String description, String status, String location, ArrayList<Bid> bids, ArrayList<Task> pictures, Float lowestBid)
        //nTask = new Task("Cleaner Joe","Cleaning","",null,null,null,null);

        //TESTING BELOW
        lstTask = new ArrayList<>();
        lstTask.add(new Task("Job 1",null,null,null,null,null,null));
        lstTask.add(new Task("Job 2",null,null,null,null,null,null));
        lstTask.add(new Task("Job 3",null,null,null,null,null,null));
        //lstTask.add(new Task("KIM"));
        //lstTask.add(new Task("JUNG"));


    }
}
