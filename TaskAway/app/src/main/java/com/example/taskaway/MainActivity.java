package com.example.taskaway;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by SJIsmail.
 * The following below creates the tab layout and the adapter for the tab fragments
 */
/**
 * DO NOT REMOVE COMMENTS
 * Those lines are for when we have server connectivity
 */

public class MainActivity extends AppCompatActivity{
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String userID = getIntent().getStringExtra("user_id");
        final String user_name = getIntent().getStringExtra("user_name");


        /**
         * TOOLBAR
         * The following below creates the layout for the Toolbar.
         * Sets a tittle for the app on the toolbar.
         * Set an Icon for the Menu button on the left side of toolbar, then creates cases where popup is clicked.
         */
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("TaskAway");
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupMenu pm = new PopupMenu(MainActivity.this,v);
                        pm.getMenuInflater().inflate(R.menu.popup_menu, pm.getMenu());
                        pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()){
                                    case R.id.Item1:
                                        Intent intent = new Intent(MainActivity.this, ViewProfile.class);
                                        intent.putExtra("user_id", userID);
                                        intent.putExtra("user_name", user_name);
                                        startActivity(intent);
                                        return true;

                                    case R.id.Item2:
                                        Toast.makeText(MainActivity.this, "About Us Clicked", Toast.LENGTH_SHORT).show();
                                        return true;
                                    case R.id.Item3:
                                        Toast.makeText(MainActivity.this, "Logout Clicked", Toast.LENGTH_SHORT).show();
                                        return true;
                                }
                                return true;
                            }
                        });
                        pm.show();
                        //Toast.makeText(MainActivity.this, "Toolbar", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        /***/




        /**
         * Creates a tab-layout with three fragments.
         */
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
        /***/



    }



    /**
     * Adds a Search button to the toolbar
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case R.id.search_id:
                Toast.makeText(this, "Search Clicked", Toast.LENGTH_SHORT).show();
                //Toast.makeText(this,"Search Clicked",Toast.LENGTH_SHORT.show());
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    /***/




}