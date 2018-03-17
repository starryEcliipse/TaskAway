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
    ImageButton imgButton;

    public MyJobs() {
    }
    /**
     * instantiate the ViewPagerAdapter class with the lstTask arraylist
     * OnCreateView adds the "Add Button" to the first Fragment tab.
     *
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.myjobs_layout, container, false);

        imgButton = (ImageButton) rootView.findViewById(R.id.img_btn_add);
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Add Clicked!", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(getContext(), AddActivity.class);
                startActivity(intent1);
           }
       });





        myrecyclerview = (RecyclerView) rootView.findViewById(R.id.jobs_recyclerview);

        TaskListViewAdapter recycleAdapter = new TaskListViewAdapter(getContext(), lstTask);

        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        myrecyclerview.setAdapter(recycleAdapter);

        return rootView;

    }

    /**
     * Testing the functionality of the arraylist and its compatibility with the layout "
     * Adds tasks to the ArrayList
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Task(String name, String description, String status, String location, ArrayList<Bid> bids, ArrayList<Task> pictures, Float lowestBid)
        //nTask = new Task("Cleaner Joe","Cleaning","",null,null,null,null);

        //TESTING BELOW
        lstTask = new ArrayList<>();
        lstTask.add(new Task("Job 1","This is a test description!","Requested","Kamloops, BC",null,null,null));
        lstTask.add(new Task("Job 2",null,null,null,null,null,null));
        lstTask.add(new Task("Job 3",null,null,null,null,null,null));
        //lstTask.add(new Task("KIM"));
        //lstTask.add(new Task("JUNG"));

    }

    // TODO: Implement Reading Data from ElasticSearch.
    // user = ServerWrapper.getUserFromID(someIDfromMain);  // always pass id
    // tasklist = user.getReqTasks();


}
