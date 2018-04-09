package com.example.taskaway;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * Displays bids that have been placed on the current task.
 * User can accept ONE bid or delete ONE bids (this is the nature of the radio button).
 *
 * @author Created by Jonathan Ismail, Edited by Katherine Mae Patenio, Adrian Schuldhaus
 * Created on 2018-04-01
 *
 * Interaction with Adapter is based on: https://stackoverflow.com/a/47183251
 *
 * @see Bid
 * @see MyBids
 * @see OtherBidsViewAdapter
 * //@see activity_view_other_bids.xml
 * //@see item_otherbids.xml
 */
public class ViewOtherBids extends AppCompatActivity implements OnBidClickListener {

    // User info
    private String user_name;
    private String user_id;

    // Adapter, Activity information to pass/update
    private OnBidClickListener onBidClickListener; // to be used for passing information to ViewOwnTask
    private Bid selectedBid;
    private int pos;
    private ArrayList<Bid> bidList = new ArrayList<Bid>();
    private Task task;
    private String taskid;
    private float lowestbidamount;

    View rootView;
    private TextView tname;
    private RecyclerView myrecyclerview;
    private RecyclerView.Adapter adapter;

    private Button acceptButton;
    private Button declineButton;


    // Toolbar
    private ImageButton toolBarBackbtn;
    private TextView toolBarTitle;
    private ImageButton toolBarSaveBtn;

    /**
     * Set layout widgets, buttons, and views.
     *
     * @param savedInstanceState - saved state
     */
    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_other_bids);

        tname = (TextView) findViewById(R.id.textView_otherbids);

        // Get information from AddTaskActivity
        Intent intent = getIntent();
        final String taskName = intent.getStringExtra("name"); // task name
        lowestbidamount = intent.getFloatExtra("lowestbidamount", 0);

        tname.setTextSize(20);

        user_name = intent.getStringExtra("username");
        taskid = intent.getStringExtra("taskid");

        SpannableString content = new SpannableString(taskName);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tname.setText(content);

        // Recyclerview layout
        myrecyclerview = (RecyclerView) findViewById(R.id.otherbids_recyclerview);
        myrecyclerview.setHasFixedSize(true);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(this));

        if (!MainActivity.isOnline()){
            Toast.makeText(getApplicationContext(),"Something went wrong - unable to get bids!", Toast.LENGTH_SHORT).show();
            Intent intent2 = new Intent(ViewOtherBids.this, ViewOwnTask.class);
            intent.putExtra("task", task); // put task
            intent.putExtra("userid", user_id);
            intent.putExtra("userName", user_name);
            startActivity(intent2);
        }

        /* READ TASK */
        if (bidList.isEmpty()) {
            task = (Task) getIntent().getSerializableExtra("task");
        }
        bidList = task.getBids(); // bids

        /* SET ADAPTER */
        adapter = new OtherBidsViewAdapter(this, bidList, this);
        myrecyclerview.setAdapter(adapter);

        /* BUTTONS */
        acceptButton = (Button) findViewById(R.id.accept_button);
        declineButton = (Button) findViewById(R.id.decline_button);

        /* TOOL BAR GO BACK TO VIEW OWN TASK */
        toolBarBackbtn = (ImageButton)findViewById(R.id.toolbar_back_btn);
        toolBarBackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewOtherBids.this, ViewOwnTask.class);
                intent.putExtra("task", task); // put task
                intent.putExtra("userid", user_id);
                intent.putExtra("userName", user_name);
                startActivity(intent);
            }
        });

        /* SAVE TOOLBAR BUTTON - REMOVE FOR THIS ACTIVITY */
        toolBarSaveBtn = (ImageButton)findViewById(R.id.toolbar_save_btn);
        toolBarSaveBtn.setVisibility(View.GONE);

        /* SET TITLE OF TOOLBAR */
        toolBarTitle = (TextView)findViewById(R.id.toolbar_title);
        toolBarTitle.setText("All Bids");

        /* ACCEPT */
        acceptButton.setOnClickListener(new View.OnClickListener(){

            /**
             * Lets the user accept a bid. All other bids in this task are DELETED.
             * @param view - instance of View
             */
            @Override
            public void onClick(View view){

                Context context = getApplicationContext();

                /* EXCEPTION HANDLING */
                // Not online
                if (!MainActivity.isOnline()){
                    Toast.makeText(getApplicationContext(), "You must be online to accept bids", Toast.LENGTH_SHORT).show();
                    return;
                }
                // No bid selected
                if (selectedBid == null){
                    Toast.makeText(context, "Please select a bid to accept!", Toast.LENGTH_LONG).show();
                    return;
                }

//                task = ServerWrapper.getJobFromId(taskid);
//                bidList = task.getBids();

                /* GET BIDDER INFORMATION FOR EACH BID */
                String CurrentBidderId = selectedBid.getUserId();
                task.setStatus("ASSIGNED");
                task.setAssignedId(CurrentBidderId);
                User bidder = ServerWrapper.getUserFromId(CurrentBidderId);

                // Display toast to tell user that they accepted a bid
                String biddername = bidder.getUsername();
                Toast.makeText(context, "You have accepted "+ biddername +"\'s " + String.format("$%.2f", selectedBid.getAmount()) + " bid!",Toast.LENGTH_LONG).show();

                /* DELETE BIDS FROM TASK */
                /* UPDATE VIEW */
                /* UPDATE BIDDERS */
                for (int i = 0; i < bidList.size(); i++){
                    Bid b = bidList.get(i);
                    User u = ServerWrapper.getUserFromId(b.getUserId());
                    u.removeBid(task);
                    ServerWrapper.updateUser(u);
                    if (i != pos){
                        bidList.remove(i);
                        adapter.notifyItemRemoved(i);
                        adapter.notifyItemRangeChanged(i, adapter.getItemCount());
                    }
                }
                task.setBids(bidList);
                ServerWrapper.updateJob(task);

                Intent intent = new Intent(ViewOtherBids.this, ViewOwnTask.class);
                intent.putExtra("task", task);
                intent.putExtra("userid", user_id);
                intent.putExtra("userName", user_name);
                startActivity(intent);

            } // end of onClick

        }); // end of onClickListener Accept


        // DECLINE
        declineButton.setOnClickListener(new View.OnClickListener(){
            /**
             * Lets the user decline a bid.
             * Deletes a selected bid from the current task's list of bids, but NOT from the bidder.
             *
             * @param view - instance of View
             */
            @Override
            public void onClick(View view){

                Context context = getApplicationContext();

                /* EXCEPTION HANDLING */
                // Not online
                if (!MainActivity.isOnline()){
                    Toast.makeText(getApplicationContext(), "You must be online to decline bids", Toast.LENGTH_SHORT).show();
                    return;
                }
                // No bid selected
                if (selectedBid == null){
                    Toast.makeText(context, "Please select a bid to decline!", Toast.LENGTH_LONG).show();
                    return;
                }
                // Task already assigned // TODO: need this at all?
                if (!task.allowsBids()){
                    Toast.makeText(context, "Your task has already been assigned!", Toast.LENGTH_LONG).show();
                    return;
                }

                /* READ BIDS FROM CURRENT TASK AND DISPLAY THEM */
                bidList = task.getBids();

                /* GET BIDDER INFORMATION FOR EACH BID */
                String CurrentBidderId = selectedBid.getUserId();
                User bidder = ServerWrapper.getUserFromId(CurrentBidderId);

                // Display toast to tell user that they deleted a bid
                String biddername = bidder.getUsername();
                Toast.makeText(context, "You have declined "+ biddername +"\'s " + String.format("$%.2f", selectedBid.getAmount()) + " bid!",Toast.LENGTH_LONG).show();

                /* DELETE BID FROM TASK */
                /* UPDATE BIDDER */
                Bid b = bidList.get(pos);
                User u = ServerWrapper.getUserFromId(b.getUserId());

                u.removeBid(task);
                bidList.remove(pos);
                ServerWrapper.updateUser(u);

                task.setBids(bidList);

                /* UPDATE VIEW */
                adapter.notifyItemRemoved(pos);
                adapter.notifyItemRangeChanged(pos, adapter.getItemCount());
                ServerWrapper.updateJob(task);

                bidList = task.getBids();




            } // end of onClick

        }); // end of onClickListener decline


    } // end of onCreate


    /**
     * Use the OnBidClickListener interface's method onBidClick to pass the current bid and its position
     * to ViewOtherBids from OtherBidsViewAdapter. This is used to ensure we know which bid has been
     * selected by user via radio buttons.
     *
     * SOURCE: https://stackoverflow.com/a/47183251
     *
     * @param bid - the bid object itself
     * @param position - the position of the bid in recycler view (NOT the task bid list)
     *
     * @see OtherBidsViewAdapter
     * @see OnBidClickListener
     */
    @Override
    public void onBidClick(Bid bid, int position){
        selectedBid = bid;
        pos = position;
    }

} // end of class