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
 * @author Created by Jonathan Ismail, Edited by Katherine Mae Patenio
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

    View rootView;
    private TextView tname;
    private RecyclerView myrecyclerview;
    private RecyclerView.Adapter adapter;
    //private static TaskList lstTask;
    private String user_name;
    private String user_id;
    private Button acceptButton;
    private Button declineButton;
    private OnBidClickListener onBidClickListener;
    private Bid bidderbid;
    private int pos;
    private int posList;
    private float lowestbidamount;

    private ImageButton toolBarBackbtn;
    private TextView toolBarTitle;
    private ImageButton toolBarSaveBtn;

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_other_bids);

        tname = (TextView) findViewById(R.id.textView_otherbids);
        Intent intent = getIntent();
        String taskName = intent.getStringExtra("name"); // task name
        lowestbidamount = intent.getFloatExtra("lowestbidamount", 0);
        tname.setTextSize(48);
        SpannableString content = new SpannableString(taskName);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tname.setText(content);


        myrecyclerview = (RecyclerView) findViewById(R.id.otherbids_recyclerview);
        myrecyclerview.setHasFixedSize(true);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(this));

        user_name = intent.getStringExtra("username");
        String taskid = intent.getStringExtra("taskid");

        // TODO: just pass the task via intent instead?
        /* READ BIDS FROM CURRENT TASK AND DISPLAY THEM */
        final SaveFileController saveFileController = new SaveFileController();
        final Context context = getApplicationContext();
        final int userindex = saveFileController.getUserIndex(context, user_name);
        final Task task = saveFileController.getTask(context, userindex, taskid);
        final ArrayList<Bid> bidList = task.getBids();

        adapter = new OtherBidsViewAdapter(this, bidList, this);

        myrecyclerview.setAdapter(adapter);

        /* BUTTONS */
        acceptButton = (Button) findViewById(R.id.accept_button);
        declineButton = (Button) findViewById(R.id.decline_button);

        // TOOL BAR GO BACK TO VIEW OWN TASK
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

        // REMOVE SAVE BUTTON FOR THIS ACTIVITY
        toolBarSaveBtn = (ImageButton)findViewById(R.id.toolbar_save_btn);
        toolBarSaveBtn.setVisibility(View.GONE);

        // SET TITLE OF TOOLBAR
        toolBarTitle = (TextView)findViewById(R.id.toolbar_title);
        toolBarTitle.setText("All Bids");

        // ACCEPT
        acceptButton.setOnClickListener(new View.OnClickListener(){

            /**
             * Lets the user accept a bid. All other bids in this task are DELETED.
             * @param view
             */
            @Override
            public void onClick(View view){
                // TODO: accept a bid

                // TODO
                // Go to back to viewowntask

                // REMOVE NON ACCEPTED BIDS
                for (int i=0; i < bidList.size(); i++){
                    String bidListID = bidList.get(i).getUserId();
                    String adapterBidListID = bidList.get(pos).getUserId();
                    if (!bidListID.equals(adapterBidListID)){ // if not the accepted bid, remove
                        bidList.remove(i);
                    }
                }

                task.setBids(bidList);
                task.setStatus("ASSIGNED");

                // Update user
                // FIXME: requester is returning null!
                User requester = saveFileController.getUserFromUserId(getApplicationContext(), user_id);

                TaskList assignedList = requester.getAssignedTasks();

                // Todo new function?
                //requester.getAssignedTasks().isEmpty()
                //assignedList.add(task);
                //requester.setAssignedTasks(assignedList);
                // TODO: saveFileController.addAssignedTask(context, );

                // Update task info
                saveFileController.updateTask(getApplicationContext(), userindex, task.getId(), task);
                // Update task to be viewed by
                saveFileController.updateTaskBids(getApplicationContext(), userindex, task, task.getId(), bidderbid);
                //User bidder = saveFileController.getUserFromUserId(getApplicationContext(), bidderbid.getUserId());
                //int bidderindex = saveFileController.getUserIndex(getApplicationContext(), bidder.getUsername());

                Intent intent = new Intent(ViewOtherBids.this, ViewOwnTask.class);
                intent.putExtra("task", task); // put task
                intent.putExtra("userid", user_id);
                intent.putExtra("userName", user_name);
                //lowestbidamount = task.findLowestBid().getAmount(); // get current lowest amount to display stuff

                //intent.putExtra("lowestbidamount", lowestbidamount);
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
                // Just get username stuff
                User bidder = saveFileController.getUserFromUserId(getApplicationContext(), bidderbid.getUserId());
                String biddername = bidder.getUsername();
                Toast.makeText(getApplicationContext(), "You have declined "+ biddername +"\'s " + String.format("$%.2f", bidderbid.getAmount()) + " bid!",Toast.LENGTH_LONG).show();
                
                bidList.remove(pos);

                task.setBids(bidList);

                // Update task info
                saveFileController.updateTask(getApplicationContext(), userindex, task.getId(), task);
                saveFileController.updateTaskBids(getApplicationContext(), userindex, task, task.getId(), bidderbid);
                User user = saveFileController.getUserFromUserId(getApplicationContext(), bidderbid.getUserId());
                int bidderindex = saveFileController.getUserIndex(getApplicationContext(), user.getUsername());

                // TODO: how to update bidder's info when task is status ASSIGNED?
                //saveFileController.deleteSingleTaskBid(getApplicationContext(), bidderindex, task.getId());

                // Update view
                adapter.notifyItemRemoved(pos);
                adapter.notifyItemRangeChanged(pos, adapter.getItemCount());

            } // end of onClick

        }); // end of onClickListener decline


    } // end of onCreate

    /**
     * Use the OnBidClickListener interface's method onBidClick to pass the current bid and its position
     * to ViewOtherBids from OtherBidsViewAdapter.
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
        //Toast.makeText(getApplicationContext(), "Our bid amount is: "+bid.getAmount(), Toast.LENGTH_LONG).show();
        bidderbid = bid;
        pos = position;
    }

} // end of class