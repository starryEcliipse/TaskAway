package com.example.taskaway;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
    private CheckBox distance_checkbox;
    private CheckBox allowsBids_checkbox;

    private ImageView unavailableIcon;

    private AllBidsListViewAdapter listAdapter;

    private RelativeLayout loadingCircle;

    private double MAXIMUM_FILTER_DISTANCE = 5000;

    static final int PERMISSIONS_REQUEST_LOCATION = 99;

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

        // DISPLAY OTHER USER TASKS - uses ServerWrapper
        lstTask = ServerWrapper.getAllJobs();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();

        updateSearchResults();
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
        distance_checkbox = view.findViewById(R.id.alljobs_distance_checkbox);
        allowsBids_checkbox = view.findViewById(R.id.alljobs_allowsBids_checkbox);
        loadingCircle = view.findViewById(R.id.loadingCircle);

        searchbox.setText("");
        distance_checkbox.setChecked(false);
        allowsBids_checkbox.setChecked(false);

        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSearchResults();
            }
        });

        distance_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                updateSearchResults();
            }
        });

        allowsBids_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
        if (loadingCircle != null) loadingCircle.setVisibility(View.VISIBLE);
        if (!MainActivity.isOnline()){
            lstTask = new TaskList();
            View view = getView();
            if (view != null){
                unavailableIcon = view.findViewById(R.id.unavailableIcon);
                unavailableIcon.setVisibility(View.VISIBLE);
            }
            updateListAdapter();
            if (loadingCircle != null) loadingCircle.setVisibility(View.GONE);
            return;
        }
        String searchString = searchbox.getText().toString();
        TaskList totalHits;
        if (searchString != null && searchString.length() > 0){
            totalHits = ServerWrapper.searchJobsTerms(searchString);
        }else{
            totalHits = ServerWrapper.getAllJobs();
        }

        if (distance_checkbox.isChecked()){
            LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            Location loc = getLastKnownLocation();
            TaskList nearby = new TaskList();
            if (loc != null){
                double latitude = loc.getLatitude();
                double longitude = loc.getLongitude();
                for (Task t : totalHits){
                    float[] results = new float[1];
                    try{
                        Location.distanceBetween(t.getLatitude(), t.getLongitude(), latitude, longitude, results);
                        if (Math.abs(results[0]) <= MAXIMUM_FILTER_DISTANCE){
                            nearby.addTask(t);
                        }
                    }catch(Exception e){
                        Log.i("AllBids", e.toString());
                    }
                }
                totalHits = nearby;
            }else{
                    Toast.makeText(getContext(), "Unable to get your current location", Toast.LENGTH_SHORT).show();
                }
        }

        if (allowsBids_checkbox.isChecked()){
            TaskList allowsBids = new TaskList();
            for (Task t : totalHits){
                if (allowsBids(t)){
                    allowsBids.addTask(t);
                }
            }
            totalHits = allowsBids;
        }
        lstTask = totalHits;
        updateListAdapter();
        if (loadingCircle != null) loadingCircle.setVisibility(View.GONE);
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

    private Location getLastKnownLocation() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        if (ContextCompat.checkSelfPermission( getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION ) == PackageManager.PERMISSION_GRANTED) {
            for (String provider : providers) {
                Location l = locationManager.getLastKnownLocation(provider);
                if (l == null) {
                    continue;
                }
                if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                    bestLocation = l;
                }
            }
        }else{
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_LOCATION);
            distance_checkbox.setChecked(false);
        }
        return bestLocation;
    }

    private boolean allowsBids(Task t) {
        String status = t.getStatus().toLowerCase();
        return (status.equals("requested") || status.equals("bidded"));
    }
}
