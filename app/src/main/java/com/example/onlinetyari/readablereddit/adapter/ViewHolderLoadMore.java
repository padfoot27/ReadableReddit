package com.example.onlinetyari.readablereddit.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.onlinetyari.readablereddit.R;

/**
 * Created by Siddharth Verma on 14/5/16.
 */
public class ViewHolderLoadMore extends RecyclerView.ViewHolder {

    public TextView loadMore;

    public ViewHolderLoadMore(View itemView) {
        super(itemView);
        loadMore = (TextView) itemView.findViewById(R.id.load);
    }

}
