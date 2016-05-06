package com.example.onlinetyari.readablereddit.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlinetyari.readablereddit.R;
import com.example.onlinetyari.readablereddit.fragment.CommentFragment;
import com.example.onlinetyari.readablereddit.pojo.CommentData;
import com.jakewharton.rxbinding.view.RxView;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import rx.functions.Action1;

/**
 * Created by Siddharth Verma on 4/5/16.
 */
public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List<CommentData> mComments;
    private Resources resources;
    private Context context;
    private static final int visibleThreshold = 5;
    private EndlessScrollListener endlessScrollListener;
    private CommentFragment.OnCommentSelectedListener onCommentSelectedListener;

    public CommentAdapter(List<CommentData> comments, Resources resources, Context context, EndlessScrollListener endlessScrollListener, CommentFragment.OnCommentSelectedListener onCommentSelectedListener) {
        this.mComments = comments;
        this.resources = resources;
        this.context = context;
        this.endlessScrollListener = endlessScrollListener;
        this.onCommentSelectedListener = onCommentSelectedListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View commentView = inflater.inflate(R.layout.comment, parent, false);

        return new ViewHolderComment(commentView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ViewHolderComment viewHolderComment = (ViewHolderComment) holder;

        // Date date = new Date(mComments.get(position).created_utc);
        Date date = new java.util.Date(Double.valueOf(mComments.get(position).created_utc).longValue()*1000);

        Date currentDate = new Date(System.currentTimeMillis());

        long diff = currentDate.getTime() - date.getTime();

        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000);
        int diffInDays = (int) (diff / (1000 * 60 * 60 * 24));

        if (diffInDays != 0) {
            viewHolderComment.time.setText(String.format(resources.getString(R.string.days), diffInDays));
        }

        else if (diffHours != 0) {
            viewHolderComment.time.setText(String.format(resources.getString(R.string.hours), diffHours));
        }

        else if (diffMinutes != 0) {
            viewHolderComment.time.setText(String.format(resources.getString(R.string.minutes), diffMinutes));
        }

        else if (diffSeconds != 0) {
            viewHolderComment.time.setText(String.format(resources.getString(R.string.seconds), diffSeconds));
        }

        else viewHolderComment.time.setText(R.string.now);


        viewHolderComment.author.setText(mComments.get(position).author);
        viewHolderComment.textView.setText(mComments.get(position).body);
        viewHolderComment.points.setText(String.format(resources.getString(R.string.points), mComments.get(position).score));
        viewHolderComment.viewMore.setText(R.string.replies);

        /*RxView.clicks(viewHolderComment.viewMore)
                .subscribe(aVoid -> {
                    onCommentSelectedListener.onCommentSelected(mComments.get(position).id, mComments.get(position).subreddit);
                });*/
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }
}

