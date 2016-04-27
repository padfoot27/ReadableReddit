package com.example.onlinetyari.readablereddit.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.onlinetyari.readablereddit.R;
import com.example.onlinetyari.readablereddit.pojo.PostData;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jakewharton.rxbinding.view.RxView;

import java.util.List;

/**
 * Created by Siddharth Verma on 23/4/16.
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    public List<PostData> mPosts;
    private Resources resources;

    public ListAdapter(List<PostData> posts, Resources resources) {
        this.mPosts = posts;
        this.resources = resources;
    }

    private static OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View itemView,int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.item_post, parent, false);

        return new ViewHolder(postView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        PostData postData = mPosts.get(position);

        TextView textView = holder.title;
        SimpleDraweeView simpleDraweeView = holder.simpleDraweeView;
        TextView comments = holder.comments;
        TextView points = holder.points;

        textView.setText(postData.title);
        String[] splitURL = postData.url.split(".");
        String url = postData.url;
        String[] a = url.split(".");
        if (postData.url != null && !postData.url.isEmpty()) {

            Uri uri = Uri.parse(url);
            simpleDraweeView.setImageURI(uri);
        }


        comments.setText(String.format(resources.getString(R.string.comments), postData.num_comments));
        points.setText(String.format(resources.getString(R.string.score), postData.score));
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView title;
        public SimpleDraweeView simpleDraweeView;
        public TextView comments;
        public TextView points;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.my_image_view);
            comments = (TextView) itemView.findViewById(R.id.comments);
            points = (TextView) itemView.findViewById(R.id.points);

            RxView.clicks(itemView)
                    .subscribe(aVoid -> {
                        if (onItemClickListener != null)
                            onItemClickListener.onItemClick(itemView,getLayoutPosition());
                    });
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            PostData postData = mPosts.get(position);

        }
    }

    public void addItems(PostData postData) {
        mPosts.add(postData);
    }
}
