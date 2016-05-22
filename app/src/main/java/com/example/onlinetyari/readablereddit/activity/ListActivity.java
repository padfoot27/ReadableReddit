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
        assert viewPager != null;
        viewPager.setOffscreenPageLimit(2);
        ListTabbedFragment listTabbedFragment = new ListTabbedFragment(getSupportFragmentManager());
        listTabbedFragment.setSubReddit(FragmentConstants.FRONT_PAGE);
        setTitle(FRONT_PAGE);
        viewPager.setAdapter(listTabbedFragment);
        viewPager.setCurrentItem(1);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        assert tabLayout != null;
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

    public void setFragment(String subReddit, Integer title) {
        ListTabbedFragment listTabbedFragment = new ListTabbedFragment(getSupportFragmentManager());
        listTabbedFragment.setSubReddit(subReddit);
        viewPager.setAdapter(listTabbedFragment);
        viewPager.setCurrentItem(1);
        mDrawer.closeDrawer(GravityCompat.START);
        setTitle(title);
    }

    public void selectDrawerItem(MenuItem menuItem) {

        ListTabbedFragment listTabbedFragment;

        switch (menuItem.getItemId()) {
            case R.id.ask_redddit :
                setFragment(FragmentConstants.ASK_REDDIT, R.string.ask_reddit);
                break;

            case R.id.pics :
                setFragment(FragmentConstants.PICS, R.string.pics);
                break;

            case R.id.todayilearned :
                setFragment(FragmentConstants.TODAY_I_LEARNED, R.string.todayilearned);
                break;

            case R.id.dataisbeautiful :
                setFragment(FragmentConstants.DATA_IS_BEAUTIFUL, R.string.dataisbeautiful);
                break;

            case R.id.funny :
                setFragment(FragmentConstants.FUNNY, R.string.funny);
                break;

            case R.id.movies :
                setFragment(FragmentConstants.MOVIES, R.string.movies);
                break;

            case R.id.music :
                setFragment(FragmentConstants.MUSIC, R.string.music);
                break;

            case R.id.philosphy :
                setFragment(FragmentConstants.PHILOSOPHY, R.string.philosphy);
                break;


            case R.id.showerthoughts :
                setFragment(FragmentConstants.SHOWER_THOUGHTS, R.string.showerthoughts);
                break;

            case R.id.tifu :
                setFragment(FragmentConstants.TIFU, R.string.tifu);
                break;

            case R.id.aww :
                setFragment(FragmentConstants.AWW, R.string.aww);
                break;

            case R.id.explainlikeimfive :
                setFragment(FragmentConstants.EXPLAINLIKEIMFIVE, R.string.explainlikeimfive);
                break;

            case R.id.books :
                setFragment(FragmentConstants.BOOKS, R.string.books);
                break;

            case R.id.science :
                setFragment(FragmentConstants.SCIENCE, R.string.science);
                break;

            case R.id.world_news :
                setFragment(FragmentConstants.WORLD_NEWS, R.string.world_news);
                break;

            case R.id.ama :
                setFragment(FragmentConstants.AMA, R.string.ama);
                break;

            case R.id.mildly_interesting :
                setFragment(FragmentConstants.MILDLY_INTERESTING, R.string.mildly_interesting);
                break;

            case R.id.documentaries :
                setFragment(FragmentConstants.DOCUMENTARIES, R.string.documentaries);
                break;

            case R.id.diy :
                setFragment(FragmentConstants.DIY, R.string.diy);
                break;

            case R.id.listen_to_this :
                setFragment(FragmentConstants.LISTEN_TO_THIS, R.string.listen_to_this);
                break;

            case R.id.personal_finance :
                setFragment(FragmentConstants.PERSONAL_FINANCE, R.string.personal_finance);
                break;

            case R.id.space :
                setFragment(FragmentConstants.SPACE, R.string.space);
                break;

            case R.id.writing_prompts :
                setFragment(FragmentConstants.WRITING_PROMPTS, R.string.writing_prompts);
                break;

            case R.id.art :
                setFragment(FragmentConstants.ART, R.string.art);
                break;

            case R.id.technology :
                setFragment(FragmentConstants.TECHNOLOGY, R.string.technology);
                break;

            case R.id.startups :
                setFragment(FragmentConstants.STARTUPS, R.string.startups);
                break;

            case R.id.front_page :
                setFragment(FragmentConstants.FRONT_PAGE, R.string.front_page);
                break;
        }
    }
}
