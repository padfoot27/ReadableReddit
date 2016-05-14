package com.example.onlinetyari.readablereddit.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.example.onlinetyari.readablereddit.R;

/**
 * Created by Siddharth Verma on 14/5/16.
 */
public class ViewHolderLoadAnim extends RecyclerView.ViewHolder {

    ProgressBar progressBar;

    public ViewHolderLoadAnim(View itemView) {
        super(itemView);
        progressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar_frag);
    }
}
