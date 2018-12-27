package com.weenta.a21demorecyclerview.bean;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 设置listViewItem 间距
 */
public class ListItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public ListItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        // 底部
        outRect.bottom = space;
    }
}
