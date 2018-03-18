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
import android.widget.ImageButton;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import java.util.ArrayList;


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
    //private static ArrayList<Task> lstTask; /* CHANGE LATER */
    private static TaskList lstTask;
    private ArrayList<Bid> blist; /* DELETE LATER - temporarily make an arraylist of bids for TESTING (see MARCH 17 2018 note)*/
    ImageButton imgButton;
    private String user_name;
    private String user_id;

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
                intent1.putExtra("username",user_name);
                intent1.putExtra("userid", user_id);
                startActivity(intent1);
           }
       });

        myrecyclerview = (RecyclerView) rootView.findViewById(R.id.jobs_recyclerview);

        TaskListViewAdapter recycleAdapter = new TaskListViewAdapter(getContext(), lstTask, user_name, user_id);

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


        // GET USERNAME AND ID FROM LOGIN - TO BE PASSED TO SOME ACTIVITIES
        if (getArguments() != null){
            Log.i("My Jobs","getArguments NOT null!");
            //Bundle bundle = new Bundle();
            user_name = getArguments().getString("username");
            Log.i("My Jobs",getArguments().getString("username")+"");
            user_id = getArguments().getString("userid");
            Log.i("My Jobs", getArguments().getString("userid")+"");
        }

        final Context context = getContext();
        SaveFileController saveFileController = new SaveFileController();
        int userIndex = saveFileController.getUserIndex(context, user_name);
        Log.i("My Jobs","userindex is "+userIndex);
        lstTask = saveFileController.getUserRequiredTasks(context, userIndex);
        //lstTask.add(new Task("Job 2",null,null,null,null,null,null));
        //lstTask.add(new Task("Job 3",null,null,null,null,null,null));

        //lstTask.add(new Task(name, des, status, null, blist, null, null));
        //lstTask.add(new Task("KIM"));
        //lstTask.add(new Task("JUNG"));

    }

    public void onStart(){
        super.onStart();
        Log.i("onStart SIZE",lstTask.size()+"");


    }

    // TODO: Implement Reading Data from ElasticSearch.
    // user = ServerWrapper.getUserFromID(someIDfromMain);  // always pass id
    // tasklist = user.getReqTasks();


}
