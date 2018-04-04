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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Displays tasks of other users. Current user can bid on these tasks.
 * Sources used:
 *      https://developer.android.com/reference/android/support/v7/widget/RecyclerView.html
 *      https://www.youtube.com/watch?v=oBhgyiBVd3k <- More examples/explanation
 *
 * @author Jonathan Ismail
 *
 * @see MainActivity
 * @see AllBidsListViewAdapter
 * @see ViewTask
 */

public class AllBids extends Fragment {
    View rootView;
    private RecyclerView myrecyclerview;
    //private ArrayList<Task> lstTask;
    private static TaskList lstTask;
    private String user_name;
    private String user_id;

    private EditText searchbox;
    private ImageButton searchbutton;
    private CheckBox checkbox;

    private AllBidsListViewAdapter listAdapter;

    /**
     * Constructor of AllBids.
     */
    public AllBids() {
    }

    /**
     * Creates RecyclerView by setting layout and adapter.
     * @param inflater - instance of LayoutInflater
     * @param container - instance of ViewGroup
     * @param savedInstanceState - saved state
     * @return inflater
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.allbids_layout, container, false);

        myrecyclerview = (RecyclerView) rootView.findViewById(R.id.allbids_recyclerview);

        updateListAdapter();

        return rootView;

    }

    /**
     * Displays all tasks of other uses. Also receives username and user id from MainActivity.
     * @param savedInstanceState - saved state
     *
     *
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // GET USERNAME AND ID FROM LOGIN - TO BE PASSED TO SOME ACTIVITIES
        if (getArguments() != null){
            Log.i("All Bids","getArguments NOT null!");
            //Bundle bundle = new Bundle();
            user_name = getArguments().getString("username");
            Log.i("All Bids",getArguments().getString("username")+"");
            user_id = getArguments().getString("userid");
            Log.i("All Bids", getArguments().getString("userid")+"");
        }
        else{
            Log.i("All Bids","getArguments is null!");

        }

        /*// DISPLAY OTHER USER TASKS - uses SaveFileController
        final Context context = getContext();
        SaveFileController saveFileController = new SaveFileController();
        int userIndex = saveFileController.getUserIndex(context, user_name);
        Log.i("All Bids","Currently getting all other tasks");
        //changed this from getEveryonesTask to getAllTasks
        lstTask = saveFileController.getAllTasks(context, userIndex);*/

        // DISPLAY OTHER USER TASKS - uses ServerWrapper
        lstTask = ServerWrapper.getAllJobs();

    }

    /**
     * Sets up the view objects once the view is created.
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        searchbox = view.findViewById(R.id.alljobs_searchbox);
        searchbutton = view.findViewById(R.id.alljobs_search_btn);
        checkbox = view.findViewById(R.id.alljobs_checkbox);

        searchbox.setText("");
        checkbox.setChecked(false);

        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSearchResults();
            }
        });

        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                updateSearchResults();
            }
        });
    }

    /**
     * Executes a search query to the server with the current search parameters.
     */
    private void updateSearchResults() {
        String searchString = searchbox.getText().toString();
        TaskList totalHits;
        if (searchString != null && searchString.length() > 0){
            TaskList descriptionHits = ServerWrapper.searchJobs("description", searchString);
            TaskList nameHits = ServerWrapper.searchJobs("name", searchString);
            totalHits = descriptionHits;
            for (Task t : nameHits){ //combines search results without duplicating
                if (!totalHits.contains(t)){
                    totalHits.add(t);
                }
            }
        }else{
            totalHits = ServerWrapper.getAllJobs();
        }

        if (checkbox.isChecked()){
            //TODO: Remove results further than 5 km away.
            Log.i("All Bids","Sort out results further than 5km away.");
        }
        int size = ServerWrapper.getAllUsers().size();
        Log.i("Number of users", "" + size);
        lstTask = totalHits;
        updateListAdapter();
    }

    /**
     * Updates the AllBidsListViewAdapter contained in this fragment, and notifies it of a data set change.
     */
    private void updateListAdapter() {
        listAdapter = new AllBidsListViewAdapter(getContext(), lstTask, user_name, user_id);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        myrecyclerview.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
        myrecyclerview.invalidate();
    }
}
