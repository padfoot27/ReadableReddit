package com.example.onlinetyari.readablereddit.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.example.onlinetyari.readablereddit.R;
import com.example.onlinetyari.readablereddit.ReadableRedditApp;
import com.example.onlinetyari.readablereddit.activity.WebViewActivity;
import com.example.onlinetyari.readablereddit.constants.IntentConstants;
import com.example.onlinetyari.readablereddit.pojo.PostData;
import com.example.onlinetyari.readablereddit.pojo.Source;
import com.jakewharton.rxbinding.view.RxView;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;


/**
 * Created by Siddharth Verma on 23/4/16.
 */
public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List<PostData> mPosts;
    private Resources resources;
    private Context context;
    public static final int TEXT = 0;
    public static final int TITLE = 1;
    public static final int IMAGE = 2;
    public static final int GIF_ = 3;
    public static final int LINK = 4;
    public static final String IMGUR_HOST_IMAGE = "i.imgur.com";
    public static final String IMGUR_HOST_IMAGE_MOBILE = "m.imgur.com";
    public static final String IMGUR_HOST_ALBUM = "a";
    public static final String IMGUR_GALLERY = "gallery";
    public static final String IMGUR_HOST = "imgur.com";
    public static final String JPG = "jpg";
    public static final String PNG = "png";
    public static final String GIF = "gif";
    public static final String GIFV = "gifv";
    private static final int visibleThreshold = 5;
    private static final int optimumResolution = 640 * 480;

    private EndlessScrollListener endlessScrollListener;

    public ListAdapter(List<PostData> posts, Resources resources, Context context, EndlessScrollListener endlessScrollListener) {
        this.mPosts = posts;
        this.resources = resources;
        this.context = context;
        this.endlessScrollListener = endlessScrollListener;
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

            case IMAGE : View v3 = inflater.inflate(R.layout.layout_viewholder_image_gif, parent, false);
                             viewHolder = new ViewHolderImageGIF(v3);
                             break;

            case GIF_ : View v4 = inflater.inflate(R.layout.layout_viewholder_image_gif, parent, false);
                       viewHolder = new ViewHolderImageGIF(v4);
                       break;

            case LINK : View v5 = inflater.inflate(R.layout.layout_viewholder_link_new, parent, false);
                        viewHolder = new ViewHolderLinkNew(v5);
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

            case IMAGE : configureTextViewImageGIF((ViewHolderImageGIF) holder, position, IMAGE);
                             break;

            case GIF_ : configureTextViewImageGIF((ViewHolderImageGIF) holder, position, GIF_);
                        break;

            case LINK : configureLinkViewNewHolder((ViewHolderLinkNew) holder, position);
                        break;

            default : configureTitleViewHolder((ViewHolderTitle) holder, position);
                      break;
        }

        if (position == getItemCount() - visibleThreshold) {
            if (endlessScrollListener != null) {
                endlessScrollListener.onLoadMore(position);
            }
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

        String[] splitString = mPosts.get(position).getUrl().split(Pattern.quote("."));

        if (Arrays.asList(splitString).contains(JPG) || Arrays.asList(splitString).contains(PNG)) {
            return IMAGE;
        }

        if (Arrays.asList(splitString).contains(GIF) || Arrays.asList(splitString).contains(GIFV)){
            return LINK;
        }

        if (url != null) {
            String host = url.getHost();
            String path = url.getPath();
            String query = url.getQuery();

            if (host.equals(IMGUR_HOST_IMAGE) || host.equals(IMGUR_HOST) || host.equals(IMGUR_HOST_IMAGE_MOBILE)) {

                if (host.equals(IMGUR_HOST_IMAGE)) {
                    image_gif = true;
                }

                String[] splitPath = path.split("/");

                if (!image_gif && splitPath.length > 1 &&
                        !splitPath[1].equals(IMGUR_HOST_ALBUM) && !splitPath[1].equals(IMGUR_GALLERY)) {
                    mPosts.get(position).setUrl(mPosts.get(position).getUrl() + ".png");
                    image_gif = true;
                }
            }
        }

        if (mPosts.get(position).selftext != null && !mPosts.get(position).selftext.isEmpty()) {
            return TEXT;
        }

        else if (image_gif){

            return IMAGE;
        }

        else if (mPosts.get(position).preview != null) {
            return LINK;
        }

        else {
            return TITLE;
        }
    }

    public void addItems(PostData postData) {
        mPosts.add(postData);
    }

    public void configureTextViewHolder(ViewHolderText viewHolderText, int position) {

        viewHolderText.textView.setText(mPosts.get(position).getTitle());
        viewHolderText.textViewText.setText(mPosts.get(position).getSelftext());
        viewHolderText.points.setText(String.format(resources.getString(R.string.score), mPosts.get(position).getScore()));
        viewHolderText.comments.setText(String.format(resources.getString(R.string.comments), mPosts.get(position).getNum_comments()));
        RxView.clicks(viewHolderText.share)
                .subscribe(aVoid -> {
                    shareFunction(mPosts.get(position).getTitle(), mPosts.get(position).getUrl());
                });

        RxView.clicks(viewHolderText.textView)
                .subscribe(aVoid -> {
                    startWebViewActivity(mPosts.get(position).getUrl());
                });
    }

    public void configureTitleViewHolder(ViewHolderTitle viewHolderTitle, int position) {


        viewHolderTitle.textView.setText(mPosts.get(position).getTitle());
        viewHolderTitle.points.setText(String.format(resources.getString(R.string.score), mPosts.get(position).getScore()));
        viewHolderTitle.comments.setText(String.format(resources.getString(R.string.comments), mPosts.get(position).getNum_comments()));
        RxView.clicks(viewHolderTitle.share)
                .subscribe(aVoid -> {
                    shareFunction(mPosts.get(position).getTitle(), mPosts.get(position).getUrl());
                });

        RxView.clicks(viewHolderTitle.textView)
                .subscribe(aVoid -> {
                    startWebViewActivity(mPosts.get(position).getUrl());
                });
    }

    public void configureLinkViewHolder(ViewHolderLink viewHolderLink, int position) {

        viewHolderLink.textView.setText(mPosts.get(position).getTitle());
        viewHolderLink.textViewText.setText(String.valueOf(mPosts.get(position).getUrl()));
        viewHolderLink.points.setText(String.format(resources.getString(R.string.score), mPosts.get(position).getScore()));
        viewHolderLink.comments.setText(String.format(resources.getString(R.string.comments), mPosts.get(position).getNum_comments()));

        RxView.clicks(viewHolderLink.share)
                .subscribe(aVoid -> {
                    shareFunction(mPosts.get(position).getTitle(), mPosts.get(position).getUrl());
                });

        RxView.clicks(viewHolderLink.textView)
                .subscribe(aVoid -> {
                    startWebViewActivity(mPosts.get(position).getUrl());
                });
    }

    public void configureTextViewImageGIF(ViewHolderImageGIF viewHolderImageGIF, int position, int type) {

        viewHolderImageGIF.textView.setText(mPosts.get(position).getTitle());

        if (type == IMAGE) {

            String url = mPosts.get(position).getUrl();
            Glide
                    .with(ReadableRedditApp.getAppContext())
                    .load(url)
                    .placeholder(R.drawable.placeholder)
                    .crossFade()
                    .into(viewHolderImageGIF.image_gif);
        }

        else {
            GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(viewHolderImageGIF.image_gif);

            Glide
                    .with(ReadableRedditApp.getAppContext())
                    .load(mPosts.get(position).getUrl())
                    .error(R.drawable.drawer)
                    .into(imageViewTarget);
        }

        viewHolderImageGIF.points.setText(String.format(resources.getString(R.string.score), mPosts.get(position).getScore()));
        viewHolderImageGIF.comments.setText(String.format(resources.getString(R.string.comments), mPosts.get(position).getNum_comments()));

        RxView.clicks(viewHolderImageGIF.share)
                .subscribe(aVoid -> {
                    shareFunction(mPosts.get(position).getTitle(), mPosts.get(position).getUrl());
                });

        RxView.clicks(viewHolderImageGIF.textView)
                .subscribe(aVoid -> {
                    startWebViewActivity(mPosts.get(position).getUrl());
                });
    }

    public void configureLinkViewNewHolder(ViewHolderLinkNew viewHolderLinkNew, int position) {
        viewHolderLinkNew.textView.setText(mPosts.get(position).getTitle());

        String imageURL = null;
        URL url = null;

        try {
            url = new URL(mPosts.get(position).getUrl());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            List<Source> sourceList = mPosts.get(position).preview.images.get(0).resolutions;
            imageURL = sourceList.get(sourceList.size() - 1).url;
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (imageURL != null) {
            Glide
                    .with(ReadableRedditApp.getAppContext())
                    .load(imageURL)
                    .placeholder(R.drawable.placeholder)
                    .crossFade()
                    .into(viewHolderLinkNew.image_gif);
        }

        else viewHolderLinkNew.image_gif.setVisibility(View.GONE);

        if (url == null) {
            viewHolderLinkNew.link.setVisibility(View.GONE);

        }

        viewHolderLinkNew.points.setText(String.format(resources.getString(R.string.score), mPosts.get(position).getScore()));
        viewHolderLinkNew.comments.setText(String.format(resources.getString(R.string.comments), mPosts.get(position).getNum_comments()));

        RxView.clicks(viewHolderLinkNew.share)
                .subscribe(aVoid -> {
                    shareFunction(mPosts.get(position).getTitle(), mPosts.get(position).getUrl());
                });

        RxView.clicks(viewHolderLinkNew.textView)
                .subscribe(aVoid -> {
                    startWebViewActivity(mPosts.get(position).getUrl());
                });

        RxView.clicks(viewHolderLinkNew.link)
                .subscribe(aVoid -> {
                    startWebViewActivity(mPosts.get(position).getUrl());
                });
    }

    public void shareFunction(String title, String link) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_SUBJECT, title);
        intent.putExtra(Intent.EXTRA_TEXT, link);
        intent.setType("text/plain");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ReadableRedditApp.getAppContext().startActivity(intent);
    }

    public void startWebViewActivity(String url) {
        Activity parentActivity = (Activity) context;
        Intent intent = new Intent(parentActivity, WebViewActivity.class);
        intent.putExtra(IntentConstants.URL, url);
        parentActivity.startActivity(intent);
    }

    public void addItem(PostData postData) {
        mPosts.add(0, postData);
        notifyItemChanged(0);
    }

    public void clear() {
        mPosts.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<PostData> postDataList) {
        mPosts.addAll(postDataList);
        notifyDataSetChanged();
    }
}
