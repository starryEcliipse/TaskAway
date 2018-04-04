package com.example.taskaway;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    private Context mContext;
    //ArrayList<Task> mData;
    private ArrayList<Bid> mData;



    public OtherBidsViewAdapter(Context mContext, ArrayList<Bid> mData) {
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

        /* USERNAME DISPLAY */
       String userid = mData.get(position).getUserId();

       SaveFileController saveFileController = new SaveFileController();
       User user = saveFileController.getUserFromUserId(this.mContext, userid);

       String username = user.getUsername();
        holder.tv_name.setText(username);

        /* BID AMOUNT DISPLAY */
        int userindex = saveFileController.getUserIndexFromCreatorID(mContext, userid);
        float amount;
        String strAmount;

        for (int i=0; i < mData.size(); i++){
            //Log.i("OTHERBIDSADAPTER","Current element is: "+mData.get(i).getI);
            if (mData.get(i).getUserId().equals(userid)){
                amount = mData.get(i).getAmount();
                strAmount = Float.toString(amount);
                holder.tv_otherbid.setText(strAmount);
                break;
            }

        }


        //saveFileController.getTask(mContext, userindex, )

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name;
        private TextView tv_otherbid;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.name_other);
            tv_otherbid = (TextView) itemView.findViewById(R.id.bid_other);

        }
    }
}

