package com.example.onlinetyari.readablereddit.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.onlinetyari.readablereddit.R;

/**
 * Created by Siddharth Verma on 28/4/16.
 */
public class ViewHolderImageGIF extends RecyclerView.ViewHolder {

    public TextView textView;
    public ImageView image_gif;
    public TextView comments;
    public TextView points;
    public Button share;

    public ViewHolderImageGIF(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.textView);
        image_gif = (ImageView) itemView.findViewById(R.id.image);
        comments = (TextView) itemView.findViewById(R.id.comments);
        points = (TextView) itemView.findViewById(R.id.points);
        share = (Button) itemView.findViewById(R.id.share);
    }
}
