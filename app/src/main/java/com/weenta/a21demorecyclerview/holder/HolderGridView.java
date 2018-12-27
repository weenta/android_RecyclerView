package com.weenta.a21demorecyclerview.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.weenta.a21demorecyclerview.R;

/**
 * holder for ForGridView
 */
public class HolderGridView extends RecyclerView.ViewHolder {
    public ImageView hImageView;
    public HolderGridView(View itemView) {
        super(itemView);
        hImageView = itemView.findViewById(R.id.img_grid_item);
    }
}
