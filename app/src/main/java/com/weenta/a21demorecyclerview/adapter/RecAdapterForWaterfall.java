package com.weenta.a21demorecyclerview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.weenta.a21demorecyclerview.L;
import com.weenta.a21demorecyclerview.R;
import com.weenta.a21demorecyclerview.holder.HolderListViewFooter;
import com.weenta.a21demorecyclerview.holder.HolderWaterfall;

import java.util.List;

public class RecAdapterForWaterfall extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private List<String> list;
    private LayoutInflater inflater;
    private int TYPE_ITEM = 1;
    private int TYPE_FOOTER = 2;

    public RecAdapterForWaterfall(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder holder;
        if(viewType == TYPE_ITEM) {
            view = inflater.inflate(R.layout.item_waterfall,parent,false);
            holder = new HolderWaterfall(view);

        } else {
            view = inflater.inflate(R.layout.footer_list_view,parent,false);
            holder = new HolderListViewFooter(view);
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof HolderListViewFooter) {
            HolderListViewFooter footerHolder = (HolderListViewFooter) holder;
        } else {
            HolderWaterfall waterfallHolder = (HolderWaterfall) holder;
            String url = list.get(position);

            Glide.with(context)
                    .load(url)
                    .thumbnail(Glide.with(context).load(R.drawable.girl_off))
                    .into(waterfallHolder.hImageView);
        }


    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    // 添加itemtype
    @Override
    public int getItemViewType(int position) {
        return position + 1 == getItemCount() ? TYPE_FOOTER : TYPE_ITEM;
    }

    // TYPE_FOOTER横跨2格居中布局
    // 与grid布局差别
    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if(holder instanceof HolderListViewFooter) {
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) lp;
            params.setFullSpan(true);
        }
    }
}
