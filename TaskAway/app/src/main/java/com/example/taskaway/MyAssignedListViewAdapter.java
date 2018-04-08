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
 * Created by KatherineMae on 4/4/2018.
 */

public class MyAssignedListViewAdapter extends RecyclerView.Adapter<MyAssignedListViewAdapter.MyViewHolder>{

    Context mContext;
    ArrayList<Task> mData;
    String userName;
    String userID;

    /**
     * Constructor of MyAssignedListViewAdapter
     * @param mContext - current context; instance of Context
     * @param mData - data containing appropriate information (other tasks)
     * @param userName - current user's username
     * @param userID - current user's id
     */
    public MyAssignedListViewAdapter(Context mContext, ArrayList<Task> mData, String userName, String userID) {
        this.mContext = mContext;
        this.mData = mData;
        this.userName = userName;
        this.userID = userID;
    }

    /**
     * Creates View Holder of MyAssignedListViewAdapter.
     * Also determines behaviour when a task is selected by user.
     *
     * @param parent
     * @param viewType
     * @return MyViewHolder
     */
    @Override
    public  MyAssignedListViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_jobs,parent, false);
        final  MyAssignedListViewAdapter.MyViewHolder vHolder = new  MyAssignedListViewAdapter.MyViewHolder(v);

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
                Toast.makeText(mContext, "My Assigned Item Clicked!", Toast.LENGTH_SHORT).show();
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
    public void onBindViewHolder( MyAssignedListViewAdapter.MyViewHolder holder, int position) {
        holder.tv_name.setText(mData.get(position).getName());
        holder.status_img.setImageResource(R.mipmap.ic_assigned);


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

