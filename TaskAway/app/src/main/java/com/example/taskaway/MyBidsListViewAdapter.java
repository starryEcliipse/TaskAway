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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by SJIsmail on 2018-03-16.
 * The Following adapter below is used in conjuction with the item_jobs.xml layout.
 * As each item in the layout holds a specifc Textview position, we are able to change the data accordingly.
 *
 */

public class MyBidsListViewAdapter extends RecyclerView.Adapter<MyBidsListViewAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<Task> mData;

    public MyBidsListViewAdapter(Context mContext, ArrayList<Task> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_jobs,parent, false);
        final MyViewHolder vHolder = new MyViewHolder(v);
        vHolder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "My Bids Item Clicked!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, ViewBidTask.class);
                int pos = vHolder.getAdapterPosition();
                intent.putExtra("task", mData.get(pos));
                String position = Integer.toString(pos);
                intent.putExtra("index", position);
                mContext.startActivity(intent);
            }
        });
        return vHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_name.setText(mData.get(position).getName());
        holder.tv_img.setImageResource(R.drawable.ic_mybids);

        //holder.img_status.setText(mTasks.getTask(position).getStatus());
        //holder.img_jobs.setText(mTasks.getTask(position).getPictures());

    }
    /**
     *
     * The Following below grabs the item position of the arraylist and displays the get.name() of that position to the Textview.
     * Textview can be found in items_job.xml
     *
     */

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout item;
        private TextView tv_name;
        private ImageView tv_img;
        //private TextView tv_status;
        //private TextView img; <- For images

        public MyViewHolder(View itemView) {
            super(itemView);
            item = (LinearLayout) itemView.findViewById(R.id.item_task);
            tv_name = (TextView) itemView.findViewById(R.id.name_jobs);
            tv_img = (ImageView) itemView.findViewById(R.id.img_jobs);
            //tv_img = (ImageView) itemView.findViewById(R.id.img_jobs);
        }
    }
}

