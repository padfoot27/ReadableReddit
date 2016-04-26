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

    private static Integer NUM_ITEMS = 3;
    String[] tabTitles = new String[] {FragmentConstants.HOT, FragmentConstants.RISING, FragmentConstants.NEW};
    public ListTabbedFragment(FragmentManager fragmentManager) {
        super(fragmentManager);
    }
    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 0 : return ListFragment.newInstance(FragmentConstants.HOT, 0);

            case 1 : return ListFragment.newInstance(FragmentConstants.RISING, 1);

            case 2 : return ListFragment.newInstance(FragmentConstants.NEW, 2);

            default : return ListFragment.newInstance(FragmentConstants.RISING, 1);
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


