package com.weenta.a21demorecyclerview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.weenta.a21demorecyclerview.L;
import com.weenta.a21demorecyclerview.R;
import com.weenta.a21demorecyclerview.bean.ListItem;
import com.weenta.a21demorecyclerview.holder.HolderListView;
import com.weenta.a21demorecyclerview.holder.HolderListViewFooter;

import java.util.List;

public class RecAdapterForListView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<ListItem> list;
    LayoutInflater inflater;
    private final int TYPE_ITEM = 1;
    private final int TYPE_FOOTER = 2;

    public RecAdapterForListView(Context context, List<ListItem> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        switch (viewType) {
            case TYPE_FOOTER:
                View footerView = inflater.inflate(R.layout.footer_list_view, parent,false);
                HolderListViewFooter holderListViewFooter = new HolderListViewFooter(footerView);
                holder = holderListViewFooter;
                break;
            case TYPE_ITEM:
            default:
                View itemView = inflater.inflate(R.layout.item_list_view, parent,false);
                HolderListView holderListView = new HolderListView(itemView);
                holder = holderListView;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof HolderListViewFooter) {
            HolderListViewFooter footerHolder = (HolderListViewFooter) holder;
            // TODO nothing?

        } else {
            HolderListView itemHolder = (HolderListView) holder;
            ListItem item = list.get(position);
            itemHolder.hTitle.setText(item.getTitle());
            itemHolder.hDesc.setText(item.getDesc());

            Glide.with(context)
                    .load(item.getUrl())
                    .thumbnail(Glide.with(context).load(R.drawable.girl_off))
                    .into(itemHolder.hImageView);
        }
    }

    // 添加itemType
    @Override
    public int getItemViewType(int position) {
        return position + 1 == getItemCount() ? TYPE_FOOTER : TYPE_ITEM;
    }
}

