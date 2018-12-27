package com.weenta.a21demorecyclerview.rec;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.weenta.a21demorecyclerview.ImageUrlConfig;
import com.weenta.a21demorecyclerview.L;
import com.weenta.a21demorecyclerview.R;
import com.weenta.a21demorecyclerview.adapter.RecAdapterForGridView;
import com.weenta.a21demorecyclerview.bean.GridItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * recyclerView实现gridView布局效果
 */
public class ForGridViewActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private GridLayoutManager gridLayoutManager;
    private List<String> urls;
    private int len;
    private List<String> list = new ArrayList<>();
    private int page = 1;
    private RecAdapterForGridView adapter;
    // listItem间距
    private int margin_bottom = 25;
    private boolean isLoading = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_gridview);
        setTitle("recyclerView实现gridView效果");

        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
        // 设置下拉刷新
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                L.i("onRefresh");
                refreshList();
            }
        });

        // 上拉加载更多
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean isUpslide = false;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                // 停止滑动
                if(newState == recyclerView.SCROLL_STATE_IDLE) {
                    int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                    int itemCount = layoutManager.getItemCount();
                    if(lastVisibleItemPosition == (itemCount -1) && isUpslide && !isLoading) {
                        // 加载下一页
                        loadMore();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                isUpslide = dy > 0 ? true : false;
            }
        });
    }

    private void loadMore() {
        page ++;
        L.i("loadMore "+page);
        getList();
    }

    private void refreshList() {
        list.clear();
        page = 1;
        getList();
        refreshLayout.setRefreshing(false);
    }


    private void initData() {
        urls = ImageUrlConfig.getUrls();
        len = urls.size();
        getList();
    }

    private void getList(){
        int startItem = page * 20;
        int endItem = (page + 1) * 20;

        if(endItem < len) {
            isLoading = true;
            for (int i = startItem; i < endItem; i++) {
                list.add(urls.get(i));
            }
            adapter.notifyDataSetChanged();
            isLoading = false;
        }

    }

    private void initView() {
        recyclerView = findViewById(R.id.recycler_grid);
        gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        // 设置间距
        GridItemDecoration decoration = new GridItemDecoration(margin_bottom);
        recyclerView.addItemDecoration(decoration);

        adapter = new RecAdapterForGridView(this,list);
        recyclerView.setAdapter(adapter);

        // 下拉刷新
        refreshLayout = findViewById(R.id.refresh_layout);

    }
}
