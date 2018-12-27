package com.weenta.a21demorecyclerview.rec;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.weenta.a21demorecyclerview.ImageUrlConfig;
import com.weenta.a21demorecyclerview.L;
import com.weenta.a21demorecyclerview.R;
import com.weenta.a21demorecyclerview.adapter.RecAdapterForWaterfall;
import com.weenta.a21demorecyclerview.bean.WaterItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * recyclerView实现瀑布流布局
 */
public class ForWaterfallActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private StaggeredGridLayoutManager layoutManager;
    private RecAdapterForWaterfall adapter;
    private List<String> list = new ArrayList<>();
    private int page = 1;
    private int pageSize = 33;
    private List<String> urls;
    private WaterItemDecoration decoration;
    private boolean isLoading = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_waterfall);
        setTitle("recyclerView实现瀑布流布局");

        initView();
        initData();
        initEvent();
    }



    private void initEvent() {
        // 下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list.clear();
                page = 1;
                getList();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        // 上拉加载更多
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean isSlideUp = false;
            // 最后几个完全可见项的位置（瀑布式布局会出现这种情况）
            private int[] lastCompletelyVisiblePositions;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    // 获取最后可见的一组item
                    StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) layoutManager;
                    if (lastCompletelyVisiblePositions == null) {
                        lastCompletelyVisiblePositions = new int[manager.getSpanCount()];
                    }
                    manager.findLastCompletelyVisibleItemPositions(lastCompletelyVisiblePositions);
                    int lastCompletelyVisibleItemPosition = findMax(lastCompletelyVisiblePositions);
                    int itemCount = layoutManager.getItemCount();

                    if(lastCompletelyVisibleItemPosition == (itemCount -1) && isSlideUp && !isLoading) {
                        // 加载下一页
                        loadMore();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                isSlideUp = dy > 0 ;
            }
        });
    }

    private void loadMore() {
        page ++;
        L.i("--->next page "+page);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        getList();
    }

    private void initData() {
        urls = ImageUrlConfig.getUrls();
        page = 1;
        getList();
    }

    private void getList() {
        isLoading = true;
        int startItem = (page - 1) * pageSize;
        int endItem = page * pageSize;
        if(endItem < urls.size()) {
            for(int i=startItem; i<endItem; i++) {
                list.add(urls.get(i));
            }
        }
        adapter.notifyDataSetChanged();
        isLoading = false;
    }

    private void initView() {
        swipeRefreshLayout = findViewById(R.id.refresh_layout);
        recyclerView = findViewById(R.id.rec_waterfall);
        // 瀑布流（错列网格布局管理器）
        layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecAdapterForWaterfall(this,list);
        recyclerView.setAdapter(adapter);

        // 设置间距
        decoration = new WaterItemDecoration(20);
        recyclerView.addItemDecoration(decoration);
    }

    // 获取数组最大值
    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }
}
