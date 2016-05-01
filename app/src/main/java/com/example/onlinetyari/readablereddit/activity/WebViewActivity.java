package com.example.onlinetyari.readablereddit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.onlinetyari.readablereddit.R;
import com.example.onlinetyari.readablereddit.ReadableRedditApp;
import com.example.onlinetyari.readablereddit.constants.IntentConstants;
import com.jakewharton.rxbinding.view.RxView;

import rx.functions.Action1;

public class WebViewActivity extends AppCompatActivity {

    private WebView webView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setBackgroundColor(getResources().getColor(R.color.fab));

        webView = (WebView) findViewById(R.id.webview);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        webView.setWebViewClient(new RedditClient());
        webView.getSettings().setJavaScriptEnabled(true);

        Intent intent = getIntent();
        String url = intent.getStringExtra(IntentConstants.URL);

        RxView.clicks(fab)
                .subscribe(aVoid -> {
                    shareFunction(url);
                });

        setTitle("Readable Reddit");
        webView.loadUrl(url);
    }

    public class RedditClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            progressBar.setVisibility(View.VISIBLE);
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            progressBar.setVisibility(View.GONE);
        }
    }

    public void shareFunction(String link) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, link);
        intent.setType("text/plain");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ReadableRedditApp.getAppContext().startActivity(intent);
    }
}
