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
import android.widget.TextView;

import com.example.onlinetyari.readablereddit.R;
import com.example.onlinetyari.readablereddit.ReadableRedditApp;
import com.example.onlinetyari.readablereddit.constants.IntentConstants;
import com.jakewharton.rxbinding.view.RxView;

import java.net.URL;

import rx.functions.Action1;

public class WebViewActivity extends AppCompatActivity {

    private WebView webView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setBackgroundColor(getResources().getColor(R.color.fab));

        webView = (WebView) findViewById(R.id.webview);

        webView.setWebViewClient(new RedditClient());
        webView.getSettings().setJavaScriptEnabled(true);

        Intent intent = getIntent();
        String urlString = intent.getStringExtra(IntentConstants.URL);

        URL url = null;

        try {
            url = new URL(urlString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        RxView.clicks(fab)
                .subscribe(aVoid -> {
                    shareFunction(urlString);
                });

        if (url == null)
            setTitle("Readable Reddit");

        else setTitle(url.getHost());

        webView.loadUrl(urlString);
    }

    public class RedditClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

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
