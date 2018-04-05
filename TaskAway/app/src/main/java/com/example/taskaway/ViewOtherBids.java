package com.example.taskaway;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;


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
    private Button deleteButton;
    private OnBidClickListener onBidClickListener;
    private Bid bidderbid;
    private int posOther;
    private int posList;

    //private ArrayList<Task> lstTask;

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_other_bids);

        tname = (TextView) findViewById(R.id.textView_otherbids);
        Intent intent = getIntent();
        String taskName = intent.getStringExtra("name"); // task name
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

        // Get and display all other bids that are not the lowest
        // assume size > 1
        // bidListOther will contain all bids that do not include lowest bid
        final ArrayList<Bid> bidListOther = new ArrayList<Bid>(bidList);
        Bid bid = task.findLowestBid();
        bidListOther.remove(bid);
        adapter = new OtherBidsViewAdapter(this, bidListOther, this);



        myrecyclerview.setAdapter(adapter);

        /* BUTTONS */
        acceptButton = (Button) findViewById(R.id.accept_button);
        deleteButton = (Button) findViewById(R.id.delete_button);

        acceptButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                // TODO: accept a bid
                // Get position of task in the actual task's list of bids
                for (int i=0;i<bidList.size();i++){
                    if (bidList.get(i).getUserId().equals(bidderbid.getUserId())){
                        posList = i;
                        break;
                    }
                }

                // TODO
                // Go to back to viewowntask

                // remove all bids except that one
                for (int i=0; i < bidList.size(); i++){
                    if (bidList.get(i).getUserId().equals(bidList.get(posList).getUserId())){
                        // ignore
                    }
                    else{
                        bidList.remove(i);
                    }
                }

                task.setBids(bidList);
                task.setStatus("ASSIGNED");

                // Update user
                User requester = saveFileController.getUserFromUserId(getApplicationContext(), user_id);

                TaskList assignedList = new TaskList();

                // Todo new function?
                //requester.getAssignedTasks().isEmpty()
                //assignedList.add(task);
                //requester.setAssignedTasks(assignedList);
                // TODO: saveFileController.addAssignedTask(context, );

                // Update task info
                saveFileController.updateTask(getApplicationContext(), userindex, task.getId(), task);
                saveFileController.updateTaskBids(getApplicationContext(), userindex, task, task.getId(), bidderbid);
                User bidder = saveFileController.getUserFromUserId(getApplicationContext(), bidderbid.getUserId());
                int bidderindex = saveFileController.getUserIndex(getApplicationContext(), bidder.getUsername());
                //saveFileController.deleteSingleTaskBid(getApplicationContext(), bidderindex, task.getId());

                // Update view
                //adapter.notifyItemRemoved(posOther);
                //adapter.notifyItemRangeChanged(posOther, adapter.getItemCount());

                Intent intent = new Intent(ViewOtherBids.this, ViewOwnTask.class);
                intent.putExtra("task", task); // put task
                intent.putExtra("userID", user_id);
                intent.putExtra("userName", user_name);
                startActivity(intent);

            } // end of onClick

        }); // end of onClickListener Accept


        deleteButton.setOnClickListener(new View.OnClickListener(){

            /**
             * Deletes a selected bid from the current task.
             *
             * @param view - instance of View
             */
            @Override
            public void onClick(View view){
                // Just get username stuff
                User bidder = saveFileController.getUserFromUserId(getApplicationContext(), bidderbid.getUserId());
                String biddername = bidder.getUsername();
                Toast.makeText(getApplicationContext(), "You have deleted "+ biddername +"\'s $"+ bidderbid.getAmount()+ " bid!",Toast.LENGTH_LONG).show();

                // Get position of task in the actual task's list of bids
                for (int i=0;i<bidList.size();i++){
                    if (bidList.get(i).getUserId().equals(bidderbid.getUserId())){
                        posList = i;
                        break;
                    }
                }

                // Remove the bid from the view
                bidListOther.remove(posOther);
                // Remove the bid from the task itself
                bidList.remove(posList);
                task.setBids(bidList);

                // Update task info
                saveFileController.updateTask(getApplicationContext(), userindex, task.getId(), task);
                saveFileController.updateTaskBids(getApplicationContext(), userindex, task, task.getId(), bidderbid);
                User user = saveFileController.getUserFromUserId(getApplicationContext(), bidderbid.getUserId());
                int bidderindex = saveFileController.getUserIndex(getApplicationContext(), user.getUsername());
                saveFileController.deleteSingleTaskBid(getApplicationContext(), bidderindex, task.getId());

                // Update view
                adapter.notifyItemRemoved(posOther);
                adapter.notifyItemRangeChanged(posOther, adapter.getItemCount());

            } // end of onClick

        }); // end of onClickListener delete


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
        posOther = position;
    }

} // end of class

