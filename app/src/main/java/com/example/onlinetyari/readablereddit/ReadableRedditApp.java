package com.example.onlinetyari.readablereddit;

import android.app.Application;
import android.content.Context;

import com.example.onlinetyari.readablereddit.api.RedditAPI;

/**
 * Created by Siddharth Verma on 26/4/16.
 */
public class ReadableRedditApp extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        RedditAPI redditAPI = new RedditAPI();
        RedditAPI.setupRedditAPI();

        context = getApplicationContext();
    }

    public static Context getAppContext() {
        return context;
    }
}
