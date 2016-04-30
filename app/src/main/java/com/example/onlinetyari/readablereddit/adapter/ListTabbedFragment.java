package com.example.onlinetyari.readablereddit.adapter;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.onlinetyari.readablereddit.constants.FragmentConstants;
import com.example.onlinetyari.readablereddit.fragment.ListFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListTabbedFragment extends FragmentStatePagerAdapter {

    public String subReddit;

    private static Integer NUM_ITEMS = 3;
    String[] tabTitles = new String[] {FragmentConstants.HOT, FragmentConstants.RISING, FragmentConstants.NEW};

    public ListTabbedFragment(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public void setSubReddit(String subReddit) {
        this.subReddit = subReddit;
    }

    @Override
    public Fragment getItem(int position) {

        ListFragment fragment;

        switch (position) {

            case 0 : fragment = ListFragment.newInstance(FragmentConstants.HOT, 0);
                     fragment.setSubReddit(subReddit);
                     return fragment;

            case 1 : fragment = ListFragment.newInstance(FragmentConstants.RISING, 1);
                     fragment.setSubReddit(subReddit);
                     return fragment;

            case 2 : fragment = ListFragment.newInstance(FragmentConstants.NEW, 2);
                     fragment.setSubReddit(subReddit);
                     return fragment;

            default : fragment = ListFragment.newInstance(FragmentConstants.RISING, 1);
                      fragment.setSubReddit(subReddit);
                      return fragment;
        }
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}


