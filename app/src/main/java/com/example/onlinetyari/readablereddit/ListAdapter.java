package com.example.onlinetyari.readablereddit;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by Siddharth Verma on 23/4/16.
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<Post> mPosts;

    public ListAdapter(List<Post> posts) {
        this.mPosts = posts;
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

        Post post = mPosts.get(position);

        TextView textView = holder.title;
        SimpleDraweeView simpleDraweeView = holder.simpleDraweeView;

        textView.setText(post.data.title);
        String[] splitURL = post.data.url.split(".");
        String url = post.data.url;
        String[] a = url.split(".");
        if (post.data.url != null && !post.data.url.isEmpty()) {
            Log.v("link", post.data.url);

            Uri uri = Uri.parse(url);
            simpleDraweeView.setImageURI(uri);
        }

        /*Uri imageUri = Uri.parse("https://i.imgur.com/tGbaZCY.jpg");
        simpleDraweeView.setImageURI(imageUri);*/

    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public SimpleDraweeView simpleDraweeView;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.my_image_view);
        }
    }
}
