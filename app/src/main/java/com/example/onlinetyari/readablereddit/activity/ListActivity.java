package com.example.onlinetyari.readablereddit.activity;

import android.os.Bundle;
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
import com.example.onlinetyari.readablereddit.api.RedditAPI;
import com.facebook.drawee.backends.pipeline.Fresco;

public class ListActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_list);

        RedditAPI redditAPI = new RedditAPI();
        RedditAPI.setupRedditAPI();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        actionBarDrawerToggle.syncState();

        ViewPager viewPager = (ViewPager) findViewById(R.id.vpPager);
        ListTabbedFragment listTabbedFragment = new ListTabbedFragment(getSupportFragmentManager());
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
}
