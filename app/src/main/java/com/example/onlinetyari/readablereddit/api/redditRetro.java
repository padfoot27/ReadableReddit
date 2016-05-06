package com.example.onlinetyari.readablereddit.api;

import com.example.onlinetyari.readablereddit.pojo.InitialDataComment;
import com.example.onlinetyari.readablereddit.pojo.InitialData;

import java.util.List;

import retrofit2.http.GET;
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
            @Query(APIConstants.limit) Integer limit,
            @Query(APIConstants.RAW_JSON) Integer raw_json);

    @GET
    @Headers("Cache-Control:max-age=3600")
    Observable<List<InitialDataComment>> getComment(
            @Url String url,
            @Query(APIConstants.before) String before,
            @Query(APIConstants.after) String after,
            @Query(APIConstants.limit) Integer limit,
            @Query(APIConstants.RAW_JSON) Integer raw_json);
}
