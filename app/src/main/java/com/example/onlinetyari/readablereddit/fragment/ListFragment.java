package com.example.onlinetyari.readablereddit.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlinetyari.readablereddit.pojo.InitialData;
import com.example.onlinetyari.readablereddit.constants.IntentConstants;
import com.example.onlinetyari.readablereddit.pojo.Post;
import com.example.onlinetyari.readablereddit.R;
import com.example.onlinetyari.readablereddit.activity.DisplayPostActivity;
import com.example.onlinetyari.readablereddit.adapter.ListAdapter;
import com.example.onlinetyari.readablereddit.api.redditRetro;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment implements ListAdapter.OnItemClickListener{

    private String title;
    private Integer page;

    public ListAdapter listAdapter;
    public RecyclerView postList;
    public InitialData initialDataReceived;
    public Context context;
    public Resources resources;

    public static ListFragment newInstance(String title, Integer page) {
        ListFragment listFragment = new ListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(IntentConstants.DISPLAY_PAGE, page);
        bundle.putString(IntentConstants.DISPLAY_TITLE, title);
        listFragment.setArguments(bundle);
        return listFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        resources = context.getResources();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getArguments().getString(IntentConstants.DISPLAY_TITLE);
        page = getArguments().getInt(IntentConstants.DISPLAY_PAGE);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onItemClick(View itemView, int position) {
        Intent intent = new Intent(context, DisplayPostActivity.class);
        intent.putExtra(IntentConstants.DISPLAY_TITLE, initialDataReceived.data.children.get(position).data.title);
        intent.putExtra(IntentConstants.DISPLAY_IMAGE, initialDataReceived.data.children.get(position).data.url);

        startActivity(intent);
    }

    @Override
    public void onResume() {

        super.onResume();
        Gson gson = new GsonBuilder().
                excludeFieldsWithoutExposeAnnotation().
                create();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.reddit.com/")
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        redditRetro redditRetroService = retrofit.create(redditRetro.class);

        postList = (RecyclerView) getView().findViewById(R.id.post_list);

        String url;

        switch (page) {

            case 0 : url = "https://api.reddit.com/r/pics/hot.json";
                     break;

            case 1 : url = "https://api.reddit.com/r/pics/rising.json";
                     break;
            case 2 : url = "https://api.reddit.com/r/pics/new.json";
                     break;
            default : url = "https://api.reddit.com/r/pics/rising.json";
        }

        redditRetroService.getData(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(initialData ->
                {

                    listAdapter = new ListAdapter(initialData.data.children, resources);
                    listAdapter.setOnItemClickListener(this);
                    initialDataReceived = initialData;
                    for (Post post : initialData.data.children)
                        Log.v("sid", post.data.url);
                    postList.setAdapter(listAdapter);
                    postList.setLayoutManager(new LinearLayoutManager(context));
                });
    }
}
