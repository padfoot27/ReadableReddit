package com.example.onlinetyari.readablereddit.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlinetyari.readablereddit.R;
import com.example.onlinetyari.readablereddit.pojo.PostData;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.net.URL;
import java.util.List;


/**
 * Created by Siddharth Verma on 23/4/16.
 */
public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List<PostData> mPosts;
    private Resources resources;
    public static final int TEXT = 0;
    public static final int TITLE = 1;
    public static final int IMAGE_GIF = 2;
    public static final int LINK = 3;
    public static final String IMGUR_HOST_IMAGE = "i.imgur.com";
    public static final String IMGUR_HOST = "imgur.com";
    public static final String IMGUR_HOST_ALBUM = "a";

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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        RecyclerView.ViewHolder viewHolder;

        switch (viewType) {
            case TEXT : View v1 = inflater.inflate(R.layout.layout_viewholder_text, parent, false);
                        viewHolder = new ViewHolderText(v1);
                        break;

            case TITLE : View v2 = inflater.inflate(R.layout.layout_viewholder_title, parent, false);
                         viewHolder = new ViewHolderTitle(v2);
                         break;

            case IMAGE_GIF : View v3 = inflater.inflate(R.layout.layout_viewholder_image_gif, parent, false);
                             viewHolder = new ViewHolderImageGIF(v3);
                             break;

            case LINK : View v4 = inflater.inflate(R.layout.layout_viewholder_link, parent, false);
                        viewHolder = new ViewHolderLink(v4);
                        break;

            default : View v = inflater.inflate(R.layout.layout_viewholder_title, parent, false);
                      viewHolder = new ViewHolderTitle(v);
                      break;
        }

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()) {

            case TEXT : configureTextViewHolder((ViewHolderText) holder, position);
                        break;
            case TITLE : configureTitleViewHolder((ViewHolderTitle) holder, position);
                         break;

            case IMAGE_GIF : configureTextViewImageGIF((ViewHolderImageGIF) holder, position);
                             break;

            case LINK : configureLinkViewHolder((ViewHolderLink) holder, position);
                        break;

            default : configureTitleViewHolder((ViewHolderTitle) holder, position);
                      break;
        }
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    @Override
    public int getItemViewType(int position) {

        boolean image_gif = false;
        URL url = null;

        try {
            url = new URL(mPosts.get(position).getUrl());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (url != null) {
            String host = url.getHost();
            String path = url.getPath();
            String query = url.getQuery();

            if (host.equals(IMGUR_HOST_IMAGE)) {
                image_gif = true;
            }

            String[] splitPath = path.split("/");

            if (host.equals(IMGUR_HOST) && splitPath.length > 1 && !splitPath[1].equals(IMGUR_HOST_ALBUM))
                image_gif = true;
        }

        if (mPosts.get(position).selftext != null && !mPosts.get(position).selftext.isEmpty()) {
            return TEXT;
        }

        else if (mPosts.get(position).selftext != null && !mPosts.get(position).selftext.isEmpty()
                && mPosts.get(position).preview == null) {
            return TITLE;
        }

        else if (image_gif){

            return IMAGE_GIF;
        }

        else {
            return LINK;
        }
    }

    public void addItems(PostData postData) {
        mPosts.add(postData);
    }

    public void configureTextViewHolder(ViewHolderText viewHolderText, int position) {

        viewHolderText.textView.setText(mPosts.get(position).getTitle());
        viewHolderText.textViewText.setText(mPosts.get(position).getSelftext());
        viewHolderText.points.setText(String.valueOf(mPosts.get(position).getScore()));
        viewHolderText.comments.setText(mPosts.get(position).getNum_comments());
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithResourceId(R.drawable.comments).build();
        viewHolderText.share.setImageURI(imageRequest.getSourceUri());
    }

    public void configureTitleViewHolder(ViewHolderTitle viewHolderTitle, int position) {

        viewHolderTitle.textView.setText(mPosts.get(position).getTitle());
        viewHolderTitle.points.setText(String .valueOf(mPosts.get(position).getScore()));
        viewHolderTitle.comments.setText(mPosts.get(position).getNum_comments());
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithResourceId(R.drawable.comments).build();
        viewHolderTitle.share.setImageURI(imageRequest.getSourceUri());
    }

    public void configureLinkViewHolder(ViewHolderLink viewHolderLink, int position) {

        viewHolderLink.textView.setText(mPosts.get(position).getTitle());
        viewHolderLink.textViewText.setText(String.valueOf(mPosts.get(position).getUrl()));
        viewHolderLink.comments.setText(mPosts.get(position).getNum_comments());
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithResourceId(R.drawable.comments).build();
        viewHolderLink.share.setImageURI(imageRequest.getSourceUri());
        viewHolderLink.points.setText(String .valueOf(mPosts.get(position).getScore()));
    }

    public void configureTextViewImageGIF(ViewHolderImageGIF viewHolderImageGIF, int position) {

        viewHolderImageGIF.textView.setText(mPosts.get(position).getTitle());
        Uri imageUri = Uri.parse(mPosts.get(position).getUrl());
        viewHolderImageGIF.image_gif.setImageURI(imageUri);
        viewHolderImageGIF.points.setText(String.valueOf(mPosts.get(position).getScore()));
        viewHolderImageGIF.comments.setText(mPosts.get(position).getNum_comments());
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithResourceId(R.drawable.comments).build();
        viewHolderImageGIF.share.setImageURI(imageRequest.getSourceUri());
    }
}
