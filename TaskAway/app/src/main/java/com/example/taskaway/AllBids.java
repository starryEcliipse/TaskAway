package com.example.taskaway;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by SJIsmail .
 */

public class AllBids extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.allbids_layout, container, false);

        /**
         * The below is a test to see if listview works on individual fragments
         * wWe can substitute the list below with the actual arraylist
         */

        String[] demo = {"Test3","Test3","Test3","Test3","Test3","Test3"};
        ListView test1_listview = (ListView) rootView.findViewById(R.id.allbids_listview);
        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>( getActivity(), android.R.layout.simple_list_item_1, demo);
        test1_listview.setAdapter(listViewAdapter);

        return rootView;
    }
}
