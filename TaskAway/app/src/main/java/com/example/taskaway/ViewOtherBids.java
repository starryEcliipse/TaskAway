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
    private Bid selectedBid;
    private int pos;
    private ArrayList<Bid> bidList;
    private Task task;
    private String taskid;
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
        taskid = intent.getStringExtra("taskid");


        /* READ BIDS FROM CURRENT TASK AND DISPLAY THEM */
        if (MainActivity.isOnline()){ // online
            task = ServerWrapper.getJobFromId(taskid);
            bidList = task.getBids();
        }else{ // offline
            SaveFileController saveFileController = new SaveFileController();
            Context context = getApplicationContext();
            int userindex = saveFileController.getUserIndex(context, user_name);
            task = saveFileController.getTask(context, userindex, taskid);
            bidList = task.getBids();
        }

        // Temporary exception catcher //TODO: make this a function
        if (bidList == null){
            Toast.makeText(getApplicationContext(),"Something went wrong - unable to get bids!", Toast.LENGTH_SHORT).show();
            Intent intent2 = new Intent(ViewOtherBids.this, ViewOwnTask.class);
            intent.putExtra("task", task); // put task
            intent.putExtra("userid", user_id);
            intent.putExtra("userName", user_name);
            startActivity(intent2);
        }

        // SET ADAPTER
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

                Context context = getApplicationContext();

                // No bid selected
                if (selectedBid == null){
                    Toast.makeText(context, "Please select a bid to decline!", Toast.LENGTH_LONG).show();
                    return;
                }

                /* READ BIDS FROM CURRENT TASK AND DISPLAY THEM */
                if (MainActivity.isOnline()){ // online
                    task = ServerWrapper.getJobFromId(taskid);
                    bidList = task.getBids();
                }else{ // offline
                    SaveFileController saveFileController = new SaveFileController();
                    int userindex = saveFileController.getUserIndex(context, user_name);
                    task = saveFileController.getTask(context, userindex, taskid);
                    bidList = task.getBids();
                }

                /* GET BIDDER INFORMATION FOR EACH BID */
                String CurrentBidderId = selectedBid.getUserId();
                task.setStatus("ASSIGNED");
                task.setAssignedId(CurrentBidderId);
                String bidderName;
                User bidder;

                if (MainActivity.isOnline()){ // Online
                    bidder = ServerWrapper.getUserFromId(CurrentBidderId);
                }
                else{ // Offline
                    SaveFileController saveFileController = new SaveFileController();
                    bidder = saveFileController.getUserFromUserId(context, selectedBid.getUserId());
                }

                // Display toast to tell user that they deleted a bid
                String biddername = bidder.getUsername();
                Toast.makeText(context, "You have accepted "+ biddername +"\'s " + String.format("$%.2f", selectedBid.getAmount()) + " bid!",Toast.LENGTH_LONG).show();

                /* DELETE BID FROM TASK */
                /* UPDATE VIEW */
                for (int i = 0; i < bidList.size(); i++){
                    if (i != pos){
                        bidList.remove(i);
                        adapter.notifyItemRemoved(i);
                        adapter.notifyItemRangeChanged(i, adapter.getItemCount());
                    }
                }
                task.setBids(bidList);

                if (MainActivity.isOnline()){
                    ServerWrapper.updateJob(task);
                }
                else{
                    SaveFileController saveFileController = new SaveFileController();
                    int userindex = saveFileController.getUserIndex(context, user_name);
                    saveFileController.updateTask(getApplicationContext(), userindex, task.getId(), task);
                    saveFileController.updateTaskBids(getApplicationContext(), userindex, task, task.getId(), selectedBid);
                    //User user = saveFileController.getUserFromUserId(getApplicationContext(), selectedBid.getUserId());
                    //int bidderindex = saveFileController.getUserIndex(getApplicationContext(), user.getUsername());
                    //saveFileController.deleteSingleTaskBid(getApplicationContext(), bidderindex, task.getId());

                }

                // TODO
                // Go to back to viewowntask

                // REMOVE NON ACCEPTED BIDS
//                for (int i=0; i < bidList.size(); i++){
//                    String bidListID = bidList.get(i).getUserId();
//                    String adapterBidListID = bidList.get(pos).getUserId();
//                    if (!bidListID.equals(adapterBidListID)){ // if not the accepted bid, remove
//                        bidList.remove(i);
//                    }
//                }
//
//                task.setBids(bidList);
//                task.setStatus("ASSIGNED");
//
//                // Update user
//                // FIXME: requester is returning null!
//                User requester = saveFileController.getUserFromUserId(getApplicationContext(), user_id);
//
//                TaskList assignedList = requester.getAssignedTasks();
//
//                // Todo new function?
//                //requester.getAssignedTasks().isEmpty()
//                //assignedList.add(task);
//                //requester.setAssignedTasks(assignedList);
//                // TODO: saveFileController.addAssignedTask(context, );
//
//                // Update task info
//                saveFileController.updateTask(getApplicationContext(), userindex, task.getId(), task);
//                // Update task to be viewed by
//                saveFileController.updateTaskBids(getApplicationContext(), userindex, task, task.getId(), selectedBid);
//                //User bidder = saveFileController.getUserFromUserId(getApplicationContext(), selectedBid.getUserId());
//                //int bidderindex = saveFileController.getUserIndex(getApplicationContext(), bidder.getUsername());
//
//                Intent intent = new Intent(ViewOtherBids.this, ViewOwnTask.class);
//                intent.putExtra("task", task); // put task
//                intent.putExtra("userid", user_id);
//                intent.putExtra("userName", user_name);
//                //lowestbidamount = task.findLowestBid().getAmount(); // get current lowest amount to display stuff
//
//                //intent.putExtra("lowestbidamount", lowestbidamount);
//                startActivity(intent);

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

                // No bid selected
                if (selectedBid == null){
                    Toast.makeText(context, "Please select a bid to decline!", Toast.LENGTH_LONG).show();
                    return;
                }

                /* READ BIDS FROM CURRENT TASK AND DISPLAY THEM */
                if (MainActivity.isOnline()){ // online
                    task = ServerWrapper.getJobFromId(taskid);
                    bidList = task.getBids();
                }else{ // offline
                    SaveFileController saveFileController = new SaveFileController();
                    int userindex = saveFileController.getUserIndex(context, user_name);
                    task = saveFileController.getTask(context, userindex, taskid);
                    bidList = task.getBids();
                }

                /* GET BIDDER INFORMATION FOR EACH BID */
                String CurrentBidderId = selectedBid.getUserId();
                String bidderName;
                User bidder;
                User requester;
                if (MainActivity.isOnline()){ // Online
                    bidder = ServerWrapper.getUserFromId(CurrentBidderId);
                    requester = ServerWrapper.getUserFromId(user_id);
                }
                else{ // Offline
                    SaveFileController saveFileController = new SaveFileController();
                    bidder = saveFileController.getUserFromUserId(context, selectedBid.getUserId());
                    requester = saveFileController.getUserFromUserId(context, user_id);
                }

                // Display toast to tell user that they deleted a bid
                String biddername = bidder.getUsername();
                Toast.makeText(context, "You have declined "+ biddername +"\'s " + String.format("$%.2f", selectedBid.getAmount()) + " bid!",Toast.LENGTH_LONG).show();


                /* DELETE BID FROM TASK */
                bidList.remove(pos);


                if (MainActivity.isOnline()){
                    Log.i("VIEWOTHERBIDS","ONLINE");
                    Task bidderTaskBidded = null;
                    String bidderId = bidder.getId();
                    task.deleteBid(bidderId);
                    ServerWrapper.updateJob(task);
                    // Read through all tasks the bidder has bid on
                    TaskList bidderBidTaskList = bidder.getBidTasks();
                    for (int i=0; i < bidderBidTaskList.size() ; i++){
                        // Task found - update it
                        if (bidderBidTaskList.get(i).getId().equals(taskid)){
                            Log.i("VIEWOTHERBIDS","TASK FOUND");
                            bidderBidTaskList.set(i,task);
                            Log.i("VIEWOWNTASK",""+bidderBidTaskList);
                            bidder.setBidTasks(bidderBidTaskList);
                            ServerWrapper.updateUser(bidder);
                            break;
                        }
                    }
                }
                else{
                    task.setBids(bidList);
                    SaveFileController saveFileController = new SaveFileController();
                    int userindex = saveFileController.getUserIndex(context, user_name);
                    saveFileController.updateTask(getApplicationContext(), userindex, task.getId(), task);
                    saveFileController.updateTaskBids(getApplicationContext(), userindex, task, task.getId(), selectedBid);
                }
                /* UPDATE VIEW */
                adapter.notifyItemRemoved(pos);
                adapter.notifyItemRangeChanged(pos, adapter.getItemCount());

            } // end of onClick

        }); // end of onClickListener decline


    } // end of onCreate

    @Override
    public void onStart(){
        super.onStart();
    }


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