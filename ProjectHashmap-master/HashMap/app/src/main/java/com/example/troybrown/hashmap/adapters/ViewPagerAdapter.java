package com.example.troybrown.hashmap.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.troybrown.hashmap.TabMapFragment;
import com.example.troybrown.hashmap.TabOneFragment;
import com.example.troybrown.hashmap.TabPostListFragment;
import com.example.troybrown.hashmap.TabPostMapFragment;

/**
 * Created by troybrown on 2/10/17.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        }

@Override
public Fragment getItem(int position) {
        switch(position){
        case 0:
        return new TabPostListFragment();
        case 1:
        return new TabPostMapFragment();
        case 2:
        return new TabOneFragment();
        case 3:
        return new TabOneFragment();
        }
        return null;
        }

@Override
public int getCount() {
        return 2;
        }

@Override
public CharSequence getPageTitle(int position) {
        switch (position){
        case 0:
        return "TOP POSTS";
        case 1:
        return "MAP VIEW";
        case 2:
        return "Tab 3";
        case 3:
        return "Tab 4";
        }
        return null;
        }
        }
