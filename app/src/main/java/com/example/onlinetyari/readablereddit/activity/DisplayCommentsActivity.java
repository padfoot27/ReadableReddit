package com.example.onlinetyari.readablereddit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.onlinetyari.readablereddit.R;
import com.example.onlinetyari.readablereddit.constants.IntentConstants;
import com.example.onlinetyari.readablereddit.fragment.CommentFragment;
import com.example.onlinetyari.readablereddit.pojo.Comment;
import com.example.onlinetyari.readablereddit.pojo.InitialDataComment;

public class DisplayCommentsActivity extends AppCompatActivity implements CommentFragment.OnCommentSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_comments);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.commentTitle);
        Intent intent = getIntent();

        String url = intent.getStringExtra(IntentConstants.URL);
        String subReddit = intent.getStringExtra(IntentConstants.SUB_REDDIT);

        addFragment(url, subReddit);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void addFragment(String url, String subReddit) {
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_placholder, CommentFragment.newFragment(url, subReddit));
        fragmentTransaction.commit();
    }

    public void addFragment(InitialDataComment comment) {
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_placholder, CommentFragment.newFragment(comment));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onCommentSelected(InitialDataComment comment) {
        addFragment(comment);
    }

    @Override
    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getFragmentManager().popBackStack();
        }

    }
}
