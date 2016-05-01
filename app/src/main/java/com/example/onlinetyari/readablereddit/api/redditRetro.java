package com.example.onlinetyari.readablereddit.api;

import com.example.onlinetyari.readablereddit.pojo.InitialData;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Siddharth Verma on 24/4/16.
 */
public interface redditRetro {

    @GET
    @Headers("Cache-Control:max-age=3600")
    Observable<InitialData> getData(
            @Url String url,
            @Query(APIConstants.before) String before,
            @Query(APIConstants.after) String after,
            @Query(APIConstants.limit) Integer limit);
}
