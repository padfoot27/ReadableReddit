package com.example.onlinetyari.readablereddit.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.example.onlinetyari.readablereddit.adapter.ListTabbedFragment;
import com.example.onlinetyari.readablereddit.R;
import com.example.onlinetyari.readablereddit.constants.FragmentConstants;

import java.util.List;

public class ListActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        actionBarDrawerToggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(navigationView);

        viewPager = (ViewPager) findViewById(R.id.vpPager);
        viewPager.setOffscreenPageLimit(2);
        ListTabbedFragment listTabbedFragment = new ListTabbedFragment(getSupportFragmentManager());
        listTabbedFragment.setSubReddit(FragmentConstants.PICS);
        setTitle("Pics");
        viewPager.setAdapter(listTabbedFragment);
        viewPager.setCurrentItem(1);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        switch (id) {
            case  R.id.action_settings : return true;

            case android.R.id.home : mDrawer.openDrawer(GravityCompat.START);
                                     return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                item -> {
                    selectDrawerItem(item);
                    return true;
                }
        );
    }

    public void selectDrawerItem(MenuItem menuItem) {

        ListTabbedFragment listTabbedFragment;

        switch (menuItem.getItemId()) {
            case R.id.ask_redddit :
                listTabbedFragment = new ListTabbedFragment(getSupportFragmentManager());
                listTabbedFragment.setSubReddit(FragmentConstants.ASK_REDDIT);
                viewPager.setAdapter(listTabbedFragment);
                viewPager.setCurrentItem(1);
                mDrawer.closeDrawer(GravityCompat.START);
                setTitle(R.string.ask_reddit);
                break;

            case R.id.pics :
                listTabbedFragment = new ListTabbedFragment(getSupportFragmentManager());
                listTabbedFragment.setSubReddit(FragmentConstants.PICS);
                viewPager.setAdapter(listTabbedFragment);
                viewPager.setCurrentItem(1);
                mDrawer.closeDrawer(GravityCompat.START);
                setTitle(R.string.pics);
                break;

            case R.id.todayilearned :
                listTabbedFragment = new ListTabbedFragment(getSupportFragmentManager());
                listTabbedFragment.setSubReddit(FragmentConstants.TODAY_I_LEARNED);
                viewPager.setAdapter(listTabbedFragment);
                viewPager.setCurrentItem(1);
                mDrawer.closeDrawer(GravityCompat.START);
                setTitle(R.string.todayilearned);
                break;

            case R.id.dataisbeautiful :
                listTabbedFragment = new ListTabbedFragment(getSupportFragmentManager());
                listTabbedFragment.setSubReddit(FragmentConstants.DATA_IS_BEAUTIFUL);
                viewPager.setAdapter(listTabbedFragment);
                viewPager.setCurrentItem(1);
                mDrawer.closeDrawer(GravityCompat.START);
                setTitle(R.string.dataisbeautiful);
                break;

            case R.id.funny :
                listTabbedFragment = new ListTabbedFragment(getSupportFragmentManager());
                listTabbedFragment.setSubReddit(FragmentConstants.FUNNY);
                viewPager.setAdapter(listTabbedFragment);
                viewPager.setCurrentItem(1);
                mDrawer.closeDrawer(GravityCompat.START);
                setTitle(R.string.funny);
                break;

            case R.id.movies :
                listTabbedFragment = new ListTabbedFragment(getSupportFragmentManager());
                listTabbedFragment.setSubReddit(FragmentConstants.MOVIES);
                viewPager.setAdapter(listTabbedFragment);
                viewPager.setCurrentItem(1);
                mDrawer.closeDrawer(GravityCompat.START);
                setTitle(R.string.movies);
                break;

            case R.id.music :
                listTabbedFragment = new ListTabbedFragment(getSupportFragmentManager());
                listTabbedFragment.setSubReddit(FragmentConstants.MUSIC);
                viewPager.setAdapter(listTabbedFragment);
                viewPager.setCurrentItem(1);
                mDrawer.closeDrawer(GravityCompat.START);
                setTitle(R.string.music);
                break;

            case R.id.philosphy :
                listTabbedFragment = new ListTabbedFragment(getSupportFragmentManager());
                listTabbedFragment.setSubReddit(FragmentConstants.PHILOSOPHY);
                viewPager.setAdapter(listTabbedFragment);
                viewPager.setCurrentItem(1);
                mDrawer.closeDrawer(GravityCompat.START);
                setTitle(R.string.philosphy);
                break;


            case R.id.showerthoughts :
                listTabbedFragment = new ListTabbedFragment(getSupportFragmentManager());
                listTabbedFragment.setSubReddit(FragmentConstants.SHOWER_THOUGHTS);
                viewPager.setAdapter(listTabbedFragment);
                viewPager.setCurrentItem(1);
                mDrawer.closeDrawer(GravityCompat.START);
                setTitle(R.string.showerthoughts);
                break;

            case R.id.tifu :
                listTabbedFragment = new ListTabbedFragment(getSupportFragmentManager());
                listTabbedFragment.setSubReddit(FragmentConstants.TIFU);
                viewPager.setAdapter(listTabbedFragment);
                viewPager.setCurrentItem(1);
                mDrawer.closeDrawer(GravityCompat.START);
                setTitle(R.string.tifu);
                break;

            case R.id.aww :
                listTabbedFragment = new ListTabbedFragment(getSupportFragmentManager());
                listTabbedFragment.setSubReddit(FragmentConstants.AWW);
                viewPager.setAdapter(listTabbedFragment);
                viewPager.setCurrentItem(1);
                mDrawer.closeDrawer(GravityCompat.START);
                setTitle(R.string.aww);
                break;

            case R.id.explainlikeimfive :
                listTabbedFragment = new ListTabbedFragment(getSupportFragmentManager());
                listTabbedFragment.setSubReddit(FragmentConstants.EXPLAINLIKEIMFIVE);
                viewPager.setAdapter(listTabbedFragment);
                viewPager.setCurrentItem(1);
                mDrawer.closeDrawer(GravityCompat.START);
                setTitle(R.string.explainlikeimfive);
                break;

            case R.id.books :
                listTabbedFragment = new ListTabbedFragment(getSupportFragmentManager());
                listTabbedFragment.setSubReddit(FragmentConstants.BOOKS);
                viewPager.setAdapter(listTabbedFragment);
                viewPager.setCurrentItem(1);
                mDrawer.closeDrawer(GravityCompat.START);
                setTitle(R.string.books);
                break;

            case R.id.science :
                listTabbedFragment = new ListTabbedFragment(getSupportFragmentManager());
                listTabbedFragment.setSubReddit(FragmentConstants.SCIENCE);
                viewPager.setAdapter(listTabbedFragment);
                viewPager.setCurrentItem(1);
                mDrawer.closeDrawer(GravityCompat.START);
                setTitle(R.string.science);
                break;

        }
    }
}
