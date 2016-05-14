package com.example.onlinetyari.readablereddit.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.onlinetyari.readablereddit.R;
import com.example.onlinetyari.readablereddit.fragment.CommentFragment;
import com.example.onlinetyari.readablereddit.pojo.CommentData;
import com.jakewharton.rxbinding.view.RxView;

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

    private static final int COMMENT = 0;
    private static final int LOAD_MORE = 1;

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

        RecyclerView.ViewHolder viewHolder;

        switch (viewType) {
            case COMMENT : View v1 = inflater.inflate(R.layout.comment, parent, false);
                           viewHolder =  new ViewHolderComment(v1);
                           break;

            case LOAD_MORE : View v2 = inflater.inflate(R.layout.load_more, parent, false);
                             viewHolder = new ViewHolderLoadMore(v2);
                             break;

            default : View v = inflater.inflate(R.layout.comment, parent, false);
                      viewHolder =  new ViewHolderComment(v);
                      break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()) {

            case COMMENT : configureCommentViewHolder((ViewHolderComment) holder, position);
                           break;

            case LOAD_MORE : configureLoadMoreViewHolder((ViewHolderLoadMore) holder, position);
                             break;

            default : configureCommentViewHolder((ViewHolderComment) holder, position);
                      break;
        }
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }

    @Override
    public int getItemViewType(int position) {

        if (mComments.get(position).author == null)
            return LOAD_MORE;

        return COMMENT;
    }

    public void configureCommentViewHolder(ViewHolderComment viewHolderComment, int position) {

        Date date = new java.util.Date(Double.valueOf(mComments.get(position).created_utc).longValue() * 1000);

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
        if (mComments.get(position).replies == null) {
            viewHolderComment.viewMore.setVisibility(View.GONE);
        }
        else {
            viewHolderComment.viewMore.setVisibility(View.VISIBLE);
            viewHolderComment.viewMore.setText(R.string.replies);
        }
        RxView.clicks(viewHolderComment.viewMore)
                .subscribe(aVoid -> {
                    onCommentSelectedListener.onCommentSelected(mComments.get(position).replies);
                });
    }

    public void configureLoadMoreViewHolder(ViewHolderLoadMore viewHolderLoadMore, int position) {
        viewHolderLoadMore.loadMore.setText(R.string.load);
        RxView.clicks(viewHolderLoadMore.loadMore)
                .subscribe(aVoid -> {
                    new MaterialDialog.Builder(context)
                            .title(R.string.dialog_title)
                            .content(R.string.dialog_content)
                            .onAny((dialog, which) -> dialog.dismiss())
                            .build()
                            .show();
                });
    }
}

