package com.example.onlinetyari.readablereddit.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.onlinetyari.readablereddit.adapter.EndlessScrollListener;
import com.example.onlinetyari.readablereddit.api.RedditAPI;
import com.example.onlinetyari.readablereddit.constants.FragmentConstants;
import com.example.onlinetyari.readablereddit.database.PostsDatabaseHelper;
import com.example.onlinetyari.readablereddit.constants.IntentConstants;
import com.example.onlinetyari.readablereddit.R;
import com.example.onlinetyari.readablereddit.activity.DisplayPostActivity;
import com.example.onlinetyari.readablereddit.adapter.ListAdapter;
import com.example.onlinetyari.readablereddit.pojo.PostData;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment implements
        ListAdapter.OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener,
        EndlessScrollListener

{
    private String title;
    private Integer page;
    public String subReddit;
    public ListAdapter listAdapter;
    public RecyclerView postList;
    public List<PostData> postDataList;
    public Context context;
    public Resources resources;
    public CompositeSubscription compositeSubscription;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String url;
    public RelativeLayout relativeLayoutProgress;
    public ProgressBar progressBar;
    public String prevAfter = null;


    public static final String BASE_URL = "https://api.reddit.com/r/";
    public static final String BASE_URL_MAIN = "https://api.reddit.com";

    public static ListFragment newInstance(String title, Integer page) {
        ListFragment listFragment = new ListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(IntentConstants.DISPLAY_PAGE, page);
        bundle.putString(IntentConstants.DISPLAY_TITLE, title);
        listFragment.setArguments(bundle);
        return listFragment;
    }

    public void setSubReddit(String subRedditValue) {
        this.subReddit = subRedditValue;
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

        View view = inflater.inflate(R.layout.fragment_list, container, false);
        relativeLayoutProgress = (RelativeLayout) view.findViewById(R.id.loadingPanel);
        postList = (RecyclerView) view.findViewById(R.id.post_list);
        listAdapter = new ListAdapter(new ArrayList<>(), resources, context, subReddit, this);
        listAdapter.setOnItemClickListener(this);
        postList.setAdapter(listAdapter);
        postList.setLayoutManager(new LinearLayoutManager(context));
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        postDataList = new ArrayList<>();

        String section;

        if (subReddit.equals(FragmentConstants.FRONT_PAGE)) {

            switch (page) {

                case 0 : url = BASE_URL_MAIN + "/hot.json";
                    section = "hot";
                    break;

                case 1 : url = BASE_URL_MAIN + "/top.json";
                    section = "rising";
                    break;
                case 2 : url = BASE_URL_MAIN +  "/new.json";
                    section = "new";
                    break;
                default : url = BASE_URL_MAIN + "/top.json";
                    section = "rising";
            }
        }
        else {
            switch (page) {

                case 0:
                    url = BASE_URL + subReddit + "/hot.json";
                    section = "hot";
                    break;

                case 1:
                    url = BASE_URL + subReddit + "/top.json";
                    section = "rising";
                    break;
                case 2:
                    url = BASE_URL + subReddit + "/new.json";
                    section = "new";
                    break;
                default:
                    url = BASE_URL + subReddit + "/top.json";
                    section = "rising";
            }
        }

        Observable<List<PostData>> network = RedditAPI.redditRetroService.getData(url, null, null, null, 1)
                            .map(initialData -> initialData.data.children)
                            .flatMap(Observable::from)
                            .map(post -> post.data)
                            .toList();

        Observable<List<PostData>> disk = PostsDatabaseHelper.getInstance(context)
                            .getAllPosts(section);

        Observable<List<PostData>> source = Observable
                .concat(disk, network)
                .first()
                .take(25);

        Subscription subscription =
                network.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).flatMap(Observable::from).take(25)
                .doOnNext(postData -> {
                    Log.v("postData", postData.getUrl());
                    PostsDatabaseHelper.getInstance(context).addPost(postData, section);
                })
                        .doOnCompleted(() -> {
                            Log.v("abc", url);
                            relativeLayoutProgress.setVisibility(View.GONE);
                        })
                        .doOnError(throwable -> Log.v("Error", "Data subscription"))
                /*.subscribe(postData1 -> {
                    if (!listAdapter.mPosts.contains(postData1)) {
                        listAdapter.mPosts.add(postData1);
                        listAdapter.notifyItemChanged(listAdapter.getItemCount() - 1);
                    }
                })*/
                .subscribe(new Subscriber<PostData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        relativeLayoutProgress.setVisibility(View.GONE);
                        Log.v("Error", "Some error occured");
                    }

                    @Override
                    public void onNext(PostData postData) {
                        if (!listAdapter.mPosts.contains(postData)) {
                            listAdapter.mPosts.add(postData);
                            listAdapter.notifyItemChanged(listAdapter.getItemCount() - 1);
                        }
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

    @Override
    public void onRefresh() {

        String before = listAdapter.mPosts.get(0).getName();
        Subscription subscription = RedditAPI.redditRetroService.getData(url, before, null, null, 1)
                .map(initialData -> initialData.data.children)
                .flatMap(Observable::from)
                .map(post -> post.data)
                .toList()
                .subscribeOn(Schedulers.io())
                .doOnError(throwable -> Log.v("error", "error"))
                .observeOn(AndroidSchedulers.mainThread())
                /*.subscribe(postDataList -> {
                    postDataList.addAll(listAdapter.mPosts);
                    listAdapter.clear();
                    listAdapter.addAll(postDataList);
                    swipeRefreshLayout.setRefreshing(false);
                })*/
                .subscribe(new Subscriber<List<PostData>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        swipeRefreshLayout.setRefreshing(false);
                        Log.v("Error", "Some error occured");
                    }

                    @Override
                    public void onNext(List<PostData> postDataList) {
                        postDataList.addAll(listAdapter.mPosts);
                        listAdapter.clear();
                        listAdapter.addAll(postDataList);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });

        compositeSubscription.add(subscription);
    }

    @Override
    public void onLoadMore(int position) {

        String after = listAdapter.mPosts.get(listAdapter.mPosts.size() - 1).getName();


        listAdapter.loader = true;
        Subscription subscription = RedditAPI.redditRetroService.getData(url, null, after, null, 1)
                .map(initialData -> initialData.data.children)
                .flatMap(Observable::from)
                .map(post -> post.data)
                .toList()
                .subscribeOn(Schedulers.io())
                .doOnError(throwable -> Log.v("error", "error"))
                .observeOn(AndroidSchedulers.mainThread())
                /*.subscribe(postDataList1 -> {
                    listAdapter.addAll(postDataList1);
                    int currentSize = listAdapter.getItemCount();
                    listAdapter.notifyItemRangeInserted(currentSize, postDataList1.size() - 1);
                })*/
                .subscribe(new Subscriber<List<PostData>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listAdapter.loader = false;
                        listAdapter.notifyDataSetChanged();
                        Log.v("Error", "Some error occured");
                    }

                    @Override
                    public void onNext(List<PostData> postDataList) {
                        listAdapter.addAll(postDataList);
                        int currentSize = listAdapter.getItemCount();
                        if (postDataList.size() == 0) {
                            listAdapter.loader = false;
                            listAdapter.notifyDataSetChanged();
                        }
                        else
                            listAdapter.notifyItemRangeInserted(currentSize, postDataList.size() - 1);
                    }
                });
        compositeSubscription.add(subscription);
    }
}
