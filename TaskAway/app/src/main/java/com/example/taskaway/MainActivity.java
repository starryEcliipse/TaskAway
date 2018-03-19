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
 *
 * Creates the tab layout and the adapter for the tab fragments.
 *
 * @author Jonathan Ismail
 *
 * @see com.example.taskaway.MyJobs
 * @see com.example.taskaway.MyBids
 * @see com.example.taskaway.AllBids
 */
public class MainActivity extends AppCompatActivity{
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    /* IMPORTANT - some comments should not be removed! */

    /**
     * Creates layout for a Toolbar with a title and Icons for Menu Button on left side of toolbar.
     * Also receives username and user id from Login activity.
     *
     * @param savedInstanceState - saved state
     *
     * @see Login
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // USERNAME + USERID
        final String userID = getIntent().getStringExtra("user_id");
        final String user_name = getIntent().getStringExtra("user_name");

        /*
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

        // onClick Listener
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            /**
             * Determines toolbar behaviour when user selects it.
             *
             * @param v - instance of View
             */
            @Override
            public void onClick(View v) {
                PopupMenu pm = new PopupMenu(MainActivity.this,v);
                pm.getMenuInflater().inflate(R.menu.popup_menu, pm.getMenu());
                pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.Item1:
                            //pass username and userID to the next activity
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
                // Show pop up
                pm.show();
                //Toast.makeText(MainActivity.this, "Toolbar", Toast.LENGTH_SHORT).show();
            } // end of onClick
             });

        // Create tab layout - add fragments
        tabLayout = (TabLayout) findViewById(R.id.tablayout_id);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        // Adding Fragment here
        MyJobs myjobs = new MyJobs();
        MyBids mybids = new MyBids();
        AllBids allbids = new AllBids();
        adapter.addFragment(myjobs,"My Jobs");
        adapter.addFragment(mybids, "My Bids");
        adapter.addFragment(allbids,"All Jobs");

        // Setup adapters
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        // PASS USERNAME + USERID TO ALL FRAGMENTS
        passUserData(user_name, userID, myjobs);
        passUserData(user_name, userID, mybids);
        passUserData(user_name, userID, allbids);

        //Will add icons for the tabs below here
        //tabLayout.getTabAt(2).setIcon(R.drawable.ic_alljobs);

    }

    /**
     * Sends username and user id received from Login activity to the three fragments.
     * Data is send using bundles.
     *
     * @param user_name - username of current user
     * @param userID - id of current user
     * @param frag - fragment to send bundle to
     *
     * @author Katherine Mae Patenio
     *
     * @see Bundle
     * @see Login
     * @see MyJobs
     * @see MyBids
     * @see AllBids
     */
    public void passUserData(String user_name, String userID, Fragment frag){
        Bundle bundle = new Bundle();
        Log.i("BUNDLE","going to bundle stuff!");
        bundle.putString("userid", userID);
        Log.i("userid",bundle.getString("userid")+"");
        bundle.putString("username", user_name);
        Log.i("username",user_name+"");
        frag.setArguments(bundle);
    }


    /**
     * Adds a Search button to the toolbar.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        return true;
    }

    /**
     * Determines behaviour of search button.
     *
     * @param item - instance of MenuItem
     * @return boolean
     */
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
}