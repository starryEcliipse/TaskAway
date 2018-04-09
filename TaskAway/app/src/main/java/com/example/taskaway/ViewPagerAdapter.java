package com.example.taskaway;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Splits the fragments into four sections which becomes the four main classes: MyJobs, MyBids, AllBids, MyAssigned
 *
 * @author Jonathan Ismail
 *
 * @see MainActivity
 * @see MyJobs
 * @see MyBids
 * @see AllBids
 * @see MyAssigned
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> listFragment = new ArrayList<>();
    private final List<String> listTitles = new ArrayList<>();

    /**
     * Constructor of ViewPagerAdapter.
     *
     * @param fm - Fragment Manager
     */
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * Get fragment at some position.
     * @param position - index
     * @return Fragment
     */
    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);
    }

    /**
     * Return number of views available.
     * @return
     */
    @Override
    public int getCount() {
        return listTitles.size();
    }

    /**
     * Get title of a page.
     * @param position
     * @return CharSequence instance - title of page
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return listTitles.get(position);
    }

    /**
     * Add a fragment to be display and contain particular TaskAway information.
     * @param fragment
     * @param title
     *
     * @see MainActivity
     */
    public void addFragment (Fragment fragment, String title){
        listFragment.add(fragment);
        listTitles.add(title);
    }
}
