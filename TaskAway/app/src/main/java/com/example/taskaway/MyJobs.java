package com.example.taskaway;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.Fragment;

import java.util.ArrayList;


/**
 * Created by SJIsmail.
 */

public class MyJobs extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.myjobs_layout, container, false);

        /**
         * We need to add the edit button to this class to display..
         * The below is a test to see if listview works on individual fragments
         * wWe can substitute the list below with the actual arraylist
         */
        String[] demo = {"Test1","Test1","Test1","Test1","Test1","Test1"};
        ListView test1_listview = (ListView) rootView.findViewById(R.id.myjobs_listview);
        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>( getActivity(), android.R.layout.simple_list_item_1, demo);
        test1_listview.setAdapter(listViewAdapter);

        return rootView;
    }

}
