package com.example.onlinetyari.readablereddit.fragment;


import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.onlinetyari.readablereddit.R;
import com.example.onlinetyari.readablereddit.adapter.CommentAdapter;
import com.example.onlinetyari.readablereddit.adapter.EndlessScrollListener;
import com.example.onlinetyari.readablereddit.api.RedditAPI;
import com.example.onlinetyari.readablereddit.constants.FragmentConstants;
import com.example.onlinetyari.readablereddit.constants.IntentConstants;
import com.example.onlinetyari.readablereddit.pojo.Comment;
import com.example.onlinetyari.readablereddit.pojo.CommentData;
import com.example.onlinetyari.readablereddit.pojo.InitialData;
import com.example.onlinetyari.readablereddit.pojo.InitialDataComment;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommentFragment extends Fragment implements EndlessScrollListener {

    public Context context;
    public Resources resources;
    public String url;
    public String subRedddit;
    public RelativeLayout relativeLayoutProgress;
    public CompositeSubscription compositeSubscription;
    public RecyclerView commentList;
    public List<CommentData> commentDataList;
    public CommentAdapter commentAdapter;
    public String callingURl;
    private OnCommentSelectedListener onCommentSelectedListener;
    public String BASE_URL = "https://api.reddit.com/r/";
    public String COMMENTS  = "/comments/";
    public String JSON = ".json";
    public InitialDataComment comment;

    public static CommentFragment newFragment(String url, String subRedddit) {
        CommentFragment commentFragment = new CommentFragment();
        Bundle bundle = new Bundle();
        bundle.putString(IntentConstants.URL, url);
        bundle.putString(IntentConstants.SUB_REDDIT, subRedddit);
        commentFragment.setArguments(bundle);
        return commentFragment;
    }

    public static CommentFragment newFragment(InitialDataComment comment) {
        CommentFragment commentFragment = new CommentFragment();
        Bundle bundle = new Bundle();
        bundle.putString(FragmentConstants.COMMENT, new Gson().toJson(comment));
        commentFragment.setArguments(bundle);
        return commentFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onCommentSelectedListener = (OnCommentSelectedListener) context;
        this.context = context;
        resources = context.getResources();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getArguments().getString(IntentConstants.URL);
        subRedddit = getArguments().getString(IntentConstants.SUB_REDDIT);
        String commentJson = getArguments().getString(FragmentConstants.COMMENT);
        comment = null;

        if (commentJson != null) {
            comment = new Gson().fromJson(commentJson, InitialDataComment.class);
        }

        compositeSubscription = new CompositeSubscription();
    }

    public CommentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comment, container, false);
        relativeLayoutProgress = (RelativeLayout) view.findViewById(R.id.loadingPanel);
        commentList = (RecyclerView) view.findViewById(R.id.comment_recycler);
        commentAdapter = new CommentAdapter(new ArrayList<>(), resources, context, this, onCommentSelectedListener);
        commentList.setAdapter(commentAdapter);
        commentList.setLayoutManager(new LinearLayoutManager(context));

        commentDataList = new ArrayList<>();

        callingURl = BASE_URL + subRedddit + COMMENTS + url + JSON;


        if (comment != null) {

            Observable<List<CommentData>> reply = Observable.just(comment)
                    .map(initialDataComment -> initialDataComment.data.children)
                    .flatMap(Observable::from)
                    .map(comment -> comment.data)
                    .toList();

            Subscription subscription =
                    reply.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread()).flatMap(Observable::from).take(25)
                            // .skipLast(1)
                            .doOnNext(postData -> {
                                Log.v("commentData", "done");
                            })
                            .doOnCompleted(() -> {
                                relativeLayoutProgress.setVisibility(View.GONE);
                            })
                            .doOnError(throwable -> Log.v("Error", "Data subscription"))
                            /*.subscribe(commentData -> {
                                if (!commentAdapter.mComments.contains(commentData)) {
                                    commentAdapter.mComments.add(commentData);
                                    commentAdapter.notifyItemChanged(commentAdapter.getItemCount() - 1);
                                }
                            })*/
                        .subscribe(new Subscriber<CommentData>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                relativeLayoutProgress.setVisibility(View.GONE);
                                Log.v("Error", "Some error occured");
                            }

                            @Override
                            public void onNext(CommentData commentData) {
                                if (!commentAdapter.mComments.contains(commentData)) {
                                    commentAdapter.mComments.add(commentData);
                                    commentAdapter.notifyItemChanged(commentAdapter.getItemCount() - 1);
                                }
                            }
                        });

            compositeSubscription.add(subscription);

        }
        else {

            Observable<List<CommentData>> network = RedditAPI.redditRetroService.getComment(callingURl, null, null, null, 1)
                    .flatMap(Observable::from)
                    .skip(1)
                    .map(initialDataComment -> initialDataComment.data.children)
                    .flatMap(Observable::from)
                    .map(comment -> comment.data)
                    .toList();

            Subscription subscription =
                    network.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread()).flatMap(Observable::from).take(25)
                            // .skipLast(1)
                            .doOnNext(postData -> {
                                Log.v("commentData", "done");
                            })
                            .doOnCompleted(() -> {
                                Log.v("abc", url);
                                relativeLayoutProgress.setVisibility(View.GONE);
                            })
                            .doOnError(throwable -> Log.v("Error", "Data subscription"))
                        .subscribe(new Subscriber<CommentData>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                relativeLayoutProgress.setVisibility(View.GONE);
                                Log.v("Error", "Some error occured");
                            }

                            @Override
                            public void onNext(CommentData commentData) {
                                if (!commentAdapter.mComments.contains(commentData)) {
                                    commentAdapter.mComments.add(commentData);
                                    commentAdapter.notifyItemChanged(commentAdapter.getItemCount() - 1);
                                }
                            }
                        });

            compositeSubscription.add(subscription);
        }
        return view;
    }

    @Override
    public void onLoadMore(int position) {

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeSubscription.unsubscribe();
    }

    public interface OnCommentSelectedListener {
        void onCommentSelected(InitialDataComment comment);
    }
}
