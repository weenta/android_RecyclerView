package com.weenta.a21demorecyclerview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.weenta.a21demorecyclerview.R;
import com.weenta.a21demorecyclerview.holder.HolderGridView;
import com.weenta.a21demorecyclerview.holder.HolderListViewFooter;

import java.util.List;

public class RecAdapterForGridView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<String> list;
    private LayoutInflater inflater;
    private int TYPE_ITEM = 1;
    private int TYPE_FOOTER = 2;


    public RecAdapterForGridView(Context context,List<String> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder holder;
        if(viewType == TYPE_FOOTER) {
            view = inflater.inflate(R.layout.footer_list_view,parent,false);
            holder = new HolderListViewFooter(view);
        } else {
            view = inflater.inflate(R.layout.item_grid_view,parent,false);
            holder = new HolderGridView(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof HolderListViewFooter) {
            HolderListViewFooter footerHolder = (HolderListViewFooter) holder;
        }else {
            HolderGridView gridViewHolder = (HolderGridView) holder;
            String url = list.get(position);
            Glide.with(context)
                    .load(url)
                    .thumbnail(Glide.with(context).load(R.drawable.girl_off))
                    .into(gridViewHolder.hImageView);
        }
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position + 1 == getItemCount() ? TYPE_FOOTER : TYPE_ITEM;
    }

    // GridLayoutManger 设置TYPE_FOOTER 横跨2格
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if(manager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) manager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == TYPE_FOOTER ? 2 : 1;
                }
            });
        }
    }
}
