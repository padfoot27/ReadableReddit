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

import com.example.onlinetyari.readablereddit.api.RedditAPI;
import com.example.onlinetyari.readablereddit.database.PostsDatabaseHelper;
import com.example.onlinetyari.readablereddit.constants.IntentConstants;
import com.example.onlinetyari.readablereddit.R;
import com.example.onlinetyari.readablereddit.activity.DisplayPostActivity;
import com.example.onlinetyari.readablereddit.adapter.ListAdapter;
import com.example.onlinetyari.readablereddit.pojo.PostData;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment implements ListAdapter.OnItemClickListener{

    private String title;
    private Integer page;

    public ListAdapter listAdapter;
    public RecyclerView postList;
    public List<PostData> postDataList;
    public Context context;
    public Resources resources;
    public CompositeSubscription compositeSubscription;

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

        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_list, container, false);
        postList = (RecyclerView) view.findViewById(R.id.post_list);
        listAdapter = new ListAdapter(new ArrayList<>(), resources);
        listAdapter.setOnItemClickListener(this);
        postList.setAdapter(listAdapter);
        postList.setLayoutManager(new LinearLayoutManager(context));

        postDataList = new ArrayList<>();

        String url;
        String section;

        switch (page) {

            case 0 : url = "https://api.reddit.com/r/askreddit/hot.json";
                     section = "hot";
                     break;

            case 1 : url = "https://api.reddit.com/r/askreddit/rising.json";
                     section = "rising";
                     break;
            case 2 : url = "https://api.reddit.com/r/askreddit/new.json";
                     section = "new";
                     break;
            default : url = "https://api.reddit.com/r/askreddit/rising.json";
                      section = "rising";
        }

        Observable<List<PostData>> network = RedditAPI.redditRetroService.getData(url)
                            .map(initialData -> initialData.data.children)
                            .flatMap(Observable::from)
                            .map(post -> post.data)
                            .toList();

        Observable<List<PostData>> disk = PostsDatabaseHelper.getInstance(context)
                            .getAllPosts(section);

        Observable<List<PostData>> source = Observable
                .concat(disk, network)
                .first();

        Subscription subscription =
                network.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).flatMap(Observable::from)
                .doOnNext(postData -> {
                    Log.v("postData", postData.getUrl());
                    PostsDatabaseHelper.getInstance(context).addPost(postData, section);
                }).doOnCompleted(() -> Log.v("abc", "completed"))
                .subscribe(postData1 -> {
                    if (!listAdapter.mPosts.contains(postData1)) {
                        listAdapter.mPosts.add(postData1);
                        listAdapter.notifyItemChanged(listAdapter.getItemCount() - 1);
                    }
                });

        compositeSubscription.add(subscription);
        return view;
    }

    @Override
    public void onItemClick(View itemView, int position) {
        Intent intent = new Intent(context, DisplayPostActivity.class);
        intent.putExtra(IntentConstants.DISPLAY_TITLE, listAdapter.mPosts.get(position).title);
        intent.putExtra(IntentConstants.DISPLAY_IMAGE, listAdapter.mPosts.get(position).url);

        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeSubscription.unsubscribe();
    }
}
