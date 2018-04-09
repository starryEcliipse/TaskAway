package com.example.taskaway;

/**
 * Use the OnBidClickListener interface's method onBidClick to pass the current bid and its position
 * to ViewOtherBids from OtherBidsViewAdapter.
 *
 * position = the position of the current item (bid) that has been selected via radio button
 * bid = the object instance of Bid itself at the current position
 *
 * SOURCE: https://stackoverflow.com/a/47183251
 *
 * @author Katherine Mae Patenio
 * Created April 4, 2018
 *
 * @see OtherBidsViewAdapter
 * @see ViewOtherBids
 *
 */
public interface OnBidClickListener {
    void onBidClick(Bid bid, int position);
}
