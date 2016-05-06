package com.example.onlinetyari.readablereddit.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.onlinetyari.readablereddit.R;

/**
 * Created by Siddharth Verma on 5/5/16.
 */
public class ViewHolderComment extends RecyclerView.ViewHolder {

    public TextView time;
    public TextView author;
    public TextView textView;
    public TextView points;
    public TextView viewMore;

    public ViewHolderComment(View itemView) {
        super(itemView);

        time = (TextView) itemView.findViewById(R.id.time);
        author = (TextView) itemView.findViewById(R.id.author);
        textView = (TextView) itemView.findViewById(R.id.comment_text);
        points = (TextView) itemView.findViewById(R.id.point_comment);
        viewMore = (TextView) itemView.findViewById(R.id.view_replies);
    }
}
