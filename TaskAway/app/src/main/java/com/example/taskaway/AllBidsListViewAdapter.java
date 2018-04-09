package com.example.taskaway;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Displays appropriate data when user goes go view all other user's tasks (AllBids).
 * Used in conjuction with the item_jobs.xml layout.
 * As each item in the layout holds a specifc Textview position, we are able to change the data accordingly.
 *
 * @author Jonathan Ismail
 * Created 2018-03-16
 *
 * @see AllBids
 * @see MainActivity
 * @see RecyclerView.Adapter
 * @see ViewTask
 */
public class AllBidsListViewAdapter extends RecyclerView.Adapter<AllBidsListViewAdapter.MyViewHolder>{
    Context mContext;
    ArrayList<Task> mData;
    String userName;
    String userID;

    /**
     * Constructor of AllBidsListViewAdapter
     * @param mContext - current context; instance of Context
     * @param mData - data containing appropriate information (other tasks)
     * @param userName - current user's username
     * @param userID - current user's id
     */
    public AllBidsListViewAdapter(Context mContext, ArrayList<Task> mData, String userName, String userID) {
        this.mContext = mContext;
        this.mData = mData;
        this.userName = userName;
        this.userID = userID;
    }

    /**
     * Creates View Holder of AllBidsListViewAdapter.
     * Also determines behaviour when a task is selected by user.
     *
     * @param parent
     * @param viewType
     * @return MyViewHolder
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_jobs,parent, false);
        final MyViewHolder vHolder = new MyViewHolder(v);

        // When task selected
        vHolder.item.setOnClickListener(new View.OnClickListener() {

            /**
             * View current task when task is selected. Go to ViewTask activity.
             * Also pass username and userid to ViewTask.
             *
             * @param view
             *
             * @see ViewTask
             */
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ViewTask.class);
                int pos = vHolder.getAdapterPosition();
                intent.putExtra("task", mData.get(pos));
                String position = Integer.toString(pos);
                intent.putExtra("userid", userID);
                intent.putExtra("userName", userName);
                intent.putExtra("index", position);
                mContext.startActivity(intent);
            }
        });

        return vHolder;
    }

    /**
     * Gets item position and displays name of task (through TextView found in items_job.xml).
     *
     * @param holder - current holder created
     * @param position - position of task
     */
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_name.setText(mData.get(position).getName());
        holder.status_img.setImageResource(R.mipmap.ic_star_icon);

        // TODO - img status and img jobs
        //holder.img_status.setText(mTasks.getTask(position).getStatus());
        //holder.img_jobs.setText(mTasks.getTask(position).getPictures());
    }

    /**
     * Get number of tasks to display.
     *
     * @return int - number of tasks to display
     */
    @Override
    public int getItemCount() {
        return mData.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout item;
        private TextView tv_name;
        private ImageView status_img;
        //private ImageView tv_img;
        //private TextView tv_status;
        //private TextView img; <- For images
        public MyViewHolder(View itemView) {
            super(itemView);

            item = (LinearLayout) itemView.findViewById(R.id.item_task);
            tv_name = (TextView) itemView.findViewById(R.id.name_jobs);
            status_img = (ImageView) itemView.findViewById(R.id.img_status);

            // TODO - img
            //tv_img = (ImageView) itemView.findViewById(R.id.img_jobs);
        }
    }
}

