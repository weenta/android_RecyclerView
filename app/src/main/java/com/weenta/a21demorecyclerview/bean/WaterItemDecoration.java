package com.weenta.a21demorecyclerview.bean;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class WaterItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public WaterItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = space;
        outRect.left = space;
        outRect.right = space;
    }
}
