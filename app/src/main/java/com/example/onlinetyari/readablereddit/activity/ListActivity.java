package com.example.onlinetyari.readablereddit.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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

    private static final String FRONT_PAGE = "Front Page";

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
        listTabbedFragment.setSubReddit(FragmentConstants.FRONT_PAGE);
        setTitle(FRONT_PAGE);
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

            case R.id.world_news :
                listTabbedFragment = new ListTabbedFragment(getSupportFragmentManager());
                listTabbedFragment.setSubReddit(FragmentConstants.WORLD_NEWS);
                viewPager.setAdapter(listTabbedFragment);
                viewPager.setCurrentItem(1);
                mDrawer.closeDrawer(GravityCompat.START);
                setTitle(R.string.world_news);
                break;

            case R.id.ama :
                listTabbedFragment = new ListTabbedFragment(getSupportFragmentManager());
                listTabbedFragment.setSubReddit(FragmentConstants.AMA);
                viewPager.setAdapter(listTabbedFragment);
                viewPager.setCurrentItem(1);
                mDrawer.closeDrawer(GravityCompat.START);
                setTitle(R.string.ama);
                break;

            case R.id.mildly_interesting :
                listTabbedFragment = new ListTabbedFragment(getSupportFragmentManager());
                listTabbedFragment.setSubReddit(FragmentConstants.MILDLY_INTERESTING);
                viewPager.setAdapter(listTabbedFragment);
                viewPager.setCurrentItem(1);
                mDrawer.closeDrawer(GravityCompat.START);
                setTitle(R.string.mildly_interesting);
                break;

            case R.id.documentaries :
                listTabbedFragment = new ListTabbedFragment(getSupportFragmentManager());
                listTabbedFragment.setSubReddit(FragmentConstants.DOCUMENTARIES);
                viewPager.setAdapter(listTabbedFragment);
                viewPager.setCurrentItem(1);
                mDrawer.closeDrawer(GravityCompat.START);
                setTitle(R.string.documentaries);
                break;

            case R.id.diy :
                listTabbedFragment = new ListTabbedFragment(getSupportFragmentManager());
                listTabbedFragment.setSubReddit(FragmentConstants.DIY);
                viewPager.setAdapter(listTabbedFragment);
                viewPager.setCurrentItem(1);
                mDrawer.closeDrawer(GravityCompat.START);
                setTitle(R.string.diy);
                break;

            case R.id.listen_to_this :
                listTabbedFragment = new ListTabbedFragment(getSupportFragmentManager());
                listTabbedFragment.setSubReddit(FragmentConstants.LISTEN_TO_THIS);
                viewPager.setAdapter(listTabbedFragment);
                viewPager.setCurrentItem(1);
                mDrawer.closeDrawer(GravityCompat.START);
                setTitle(R.string.listen_to_this);
                break;

            case R.id.personal_finance :
                listTabbedFragment = new ListTabbedFragment(getSupportFragmentManager());
                listTabbedFragment.setSubReddit(FragmentConstants.PERSONAL_FINANCE);
                viewPager.setAdapter(listTabbedFragment);
                viewPager.setCurrentItem(1);
                mDrawer.closeDrawer(GravityCompat.START);
                setTitle(R.string.personal_finance);
                break;

            case R.id.space :
                listTabbedFragment = new ListTabbedFragment(getSupportFragmentManager());
                listTabbedFragment.setSubReddit(FragmentConstants.SPACE);
                viewPager.setAdapter(listTabbedFragment);
                viewPager.setCurrentItem(1);
                mDrawer.closeDrawer(GravityCompat.START);
                setTitle(R.string.space);
                break;

            case R.id.writing_prompts :
                listTabbedFragment = new ListTabbedFragment(getSupportFragmentManager());
                listTabbedFragment.setSubReddit(FragmentConstants.WRITING_PROMPTS);
                viewPager.setAdapter(listTabbedFragment);
                viewPager.setCurrentItem(1);
                mDrawer.closeDrawer(GravityCompat.START);
                setTitle(R.string.writing_prompts);
                break;

            case R.id.art :
                listTabbedFragment = new ListTabbedFragment(getSupportFragmentManager());
                listTabbedFragment.setSubReddit(FragmentConstants.ART);
                viewPager.setAdapter(listTabbedFragment);
                viewPager.setCurrentItem(1);
                mDrawer.closeDrawer(GravityCompat.START);
                setTitle(R.string.art);
                break;

            case R.id.technology :
                listTabbedFragment = new ListTabbedFragment(getSupportFragmentManager());
                listTabbedFragment.setSubReddit(FragmentConstants.TECHNOLOGY);
                viewPager.setAdapter(listTabbedFragment);
                viewPager.setCurrentItem(1);
                mDrawer.closeDrawer(GravityCompat.START);
                setTitle(R.string.technology);
                break;

            case R.id.startups :
                listTabbedFragment = new ListTabbedFragment(getSupportFragmentManager());
                listTabbedFragment.setSubReddit(FragmentConstants.STARTUPS);
                viewPager.setAdapter(listTabbedFragment);
                viewPager.setCurrentItem(1);
                mDrawer.closeDrawer(GravityCompat.START);
                setTitle(R.string.startups);
                break;

            case R.id.front_page :
                listTabbedFragment = new ListTabbedFragment(getSupportFragmentManager());
                listTabbedFragment.setSubReddit(FragmentConstants.FRONT_PAGE);
                viewPager.setAdapter(listTabbedFragment);
                viewPager.setCurrentItem(1);
                mDrawer.closeDrawer(GravityCompat.START);
                setTitle(R.string.front_page);
                break;


        }
    }
}
