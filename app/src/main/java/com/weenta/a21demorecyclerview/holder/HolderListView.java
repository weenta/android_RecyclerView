package com.weenta.a21demorecyclerview.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.weenta.a21demorecyclerview.R;

/**
 * forListView item Holder
 */
public class HolderListView extends RecyclerView.ViewHolder{
    public ImageView hImageView;
    public TextView hTitle, hDesc;

    public HolderListView(View itemView) {
        super(itemView);
        hImageView = itemView.findViewById(R.id.image_view);
        hTitle = itemView.findViewById(R.id.tv_title);
        hDesc = itemView.findViewById(R.id.tv_desc);
    }
}
