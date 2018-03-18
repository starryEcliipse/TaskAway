package com.example.taskaway;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
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
import java.util.Collections;
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
    private ArrayList<Bid> blist; /* DELETE LATER - temporarily make an arraylist of bids for TESTING (see MARCH 17 2018 note)*/
    ImageButton imgButton;
    private Task task;
    private String name;
    private String des;
    private String status;

    public MyJobs() {
    }
    /**
     * instantiate the ViewPagerAdapter class with the lstTask arraylist
     * OnCreateView adds the "Add Button" to the first Fragment tab.
     *
     */

    public static MyJobs newInstance(Task task){
        MyJobs fragment = new MyJobs();
        Bundle bundle = new Bundle();
        bundle.putSerializable("some task", task);
        fragment.setArguments(bundle);
        return fragment;
    }

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

        // TESTING BELOW
        /*
            ADDED MARCH 17 2018
            FIXME: Ensure we can read Tasks with an empty arraylist of bids aka read Tasks with NO bids!

            NOTE:
            I added stuff labeled with "DELETE LATER" just for testing (too lazy to make an actual test).
            I can confirm that ViewOwnTask will find the lowest bid, but it WILL CRASH if no bids exist!
            I had to make Task AND Bid classes Serializable!
                TODO: use exception handling
             - Katherine
         */
        // MAKE NEW BIDS
        Bid b=new Bid("kpatenio",(float)23.1); /* DELETE LATER*/
        Bid c=new Bid("kpatenio",(float)5); /* DELETE LATER*/
        Bid d=new Bid("kpatenio",(float)500.23); /* DELETE LATER */
        Bid e=new Bid("kpatenio",(float)4.999); /* DELETE LATER  */

        // MAKE ARRAYLIST OF BIDS
        blist = new ArrayList<Bid>(); /* DELETE LATER - make arraylist of bids */

        // ADD BIDS TO ARRAYLIST OF BIDS
        blist.add(b); /* DELETE LATER  */
        blist.add(c); /* DELETE LATER  */
        blist.add(d); /* DELETE LATER  */
        blist.add(e); /* DELETE LATER  */

        // FIND LOWEST BID
        Collections.sort(blist); /* DELETE LATER - from findLowestBid method in Task class */
        Bid bidbid = blist.get(0); /* DELETE LATER - from findLowestBid method in Task class */
        float blow = bidbid.getAmount(); /* DELETE LATER - get bid amount of lowest bid in arraylist of bids */

//        Bundle bundle = new Bundle();
//        name = bundle.getString("task");
//        des = bundle.getString("task");
//        status = bundle.getString("task");

        // ADD TEMPORARY TASKS FOR TESTING
        lstTask = new ArrayList<>();
        lstTask.add(new Task("Katherine's Taskuhhhh","This is a test description!","Requested","Kamloops, BC", blist, null, blow));
        lstTask.add(new Task("Job 2",null,null,null,null,null,null));
        lstTask.add(new Task("Job 3",null,null,null,null,null,null));
      //  lstTask.add(new Task(name, des, status, null, null, null, null));
        //lstTask.add(new Task("KIM"));
        //lstTask.add(new Task("JUNG"));

    }

    // TODO: Implement Reading Data from ElasticSearch.
    // user = ServerWrapper.getUserFromID(someIDfromMain);  // always pass id
    // tasklist = user.getReqTasks();


}
