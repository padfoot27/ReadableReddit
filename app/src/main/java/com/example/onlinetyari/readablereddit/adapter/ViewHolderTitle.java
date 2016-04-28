package com.example.onlinetyari.readablereddit.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.onlinetyari.readablereddit.R;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by Siddharth Verma on 28/4/16.
 */
public class ViewHolderTitle extends RecyclerView.ViewHolder {

    public TextView textView;
    public TextView comments;
    public TextView points;
    public SimpleDraweeView share;

    public ViewHolderTitle(View itemView) {
        super(itemView);

        textView = (TextView) itemView.findViewById(R.id.textView);
        comments = (TextView) itemView.findViewById(R.id.comments);
        points = (TextView) itemView.findViewById(R.id.points);
        share = (SimpleDraweeView) itemView.findViewById(R.id.share);
    }
}
