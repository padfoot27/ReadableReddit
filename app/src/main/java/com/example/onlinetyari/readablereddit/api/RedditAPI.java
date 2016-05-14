package com.example.onlinetyari.readablereddit.api;

import com.example.onlinetyari.readablereddit.CommentDataDeserializer;
import com.example.onlinetyari.readablereddit.pojo.CommentData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Siddharth Verma on 26/4/16.
 */
public class RedditAPI {

    public static redditRetro redditRetroService;
    public static OkHttpClient okHttpClient;

    public static void setupRedditAPI() {
        Gson gson = new GsonBuilder().
                registerTypeAdapter(CommentData.class, new CommentDataDeserializer()).
                excludeFieldsWithoutExposeAnnotation().
                create();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

        httpClientBuilder.addInterceptor(logging);
        httpClientBuilder.addNetworkInterceptor(new CacheInterceptor());

        okHttpClient = httpClientBuilder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.reddit.com/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        redditRetroService = retrofit.create(redditRetro.class);
    }
}
