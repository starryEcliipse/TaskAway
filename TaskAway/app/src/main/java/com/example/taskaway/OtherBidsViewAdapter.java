package com.example.taskaway;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by SJIsmail on 2018-04-01.
 */

public class OtherBidsViewAdapter extends RecyclerView.Adapter<OtherBidsViewAdapter.MyViewHolder>{
    Context mContext;
    ArrayList<Task> mData;
    String userName;
    String userID;


    public OtherBidsViewAdapter(Context mContext, ArrayList<Task> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_otherbids,parent, false);
        MyViewHolder vHolder = new MyViewHolder(v);
        return vHolder;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_name.setText(mData.get(position).getName());


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.name_other);

        }
    }
}

