package com.example.taskaway;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
/**
 * Created by SJIsmail.
 * The following below creates the tab layout and the adapter for the tab fragments
 */

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TOOLBAR TITLE AND MENU ICONS
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.setTitle("TaskAway");
        setSupportActionBar(toolbar);


        tabLayout = (TabLayout) findViewById(R.id.tablayout_id);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        // Adding Fragment here
        adapter.addFragment(new MyJobs(),"My Jobs");
        adapter.addFragment(new MyBids(),"My Bids");
        adapter.addFragment(new AllBids(),"All Jobs");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        //Will add icons for the tabs below here
        //tabLayout.getTabAt(2).setIcon(R.drawable.ic_alljobs);




    }


}