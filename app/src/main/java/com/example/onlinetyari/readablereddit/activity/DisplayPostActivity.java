package com.example.onlinetyari.readablereddit.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.onlinetyari.readablereddit.constants.IntentConstants;
import com.example.onlinetyari.readablereddit.R;

public class DisplayPostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_post);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();

        String title = intent.getStringExtra(IntentConstants.DISPLAY_TITLE);
        String url = intent.getStringExtra(IntentConstants.DISPLAY_IMAGE);

        TextView textView = (TextView) findViewById(R.id.title_display);

        textView.setText(title);
        Uri uri = Uri.parse(url);
    }

}
