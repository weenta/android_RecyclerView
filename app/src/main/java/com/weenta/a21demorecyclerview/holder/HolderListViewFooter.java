package com.weenta.a21demorecyclerview.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.weenta.a21demorecyclerview.R;

/**
 *  itemFooter Holder for both ForListView and ForGridView
 */
public class HolderListViewFooter extends RecyclerView.ViewHolder {
    public ProgressBar progressBar;
    public HolderListViewFooter(View itemView) {
        super(itemView);
        progressBar = itemView.findViewById(R.id.progress_bar);
    }
}
