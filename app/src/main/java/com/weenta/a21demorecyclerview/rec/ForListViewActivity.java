package com.weenta.a21demorecyclerview.rec;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.weenta.a21demorecyclerview.ImageUrlConfig;
import com.weenta.a21demorecyclerview.L;
import com.weenta.a21demorecyclerview.R;
import com.weenta.a21demorecyclerview.adapter.RecAdapterForListView;
import com.weenta.a21demorecyclerview.bean.ListItem;
import com.weenta.a21demorecyclerview.bean.ListItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * recyclerView 实现listView布局效果
 */
public class ForListViewActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<ListItem> list = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;

    // adapter
    private RecAdapterForListView adapter;
    // layoutManager
    private LinearLayoutManager linearLayoutManager;

    // listItem间距
    private int margin_bottom = 25;

    private static final String title = "list item title_";
    private static final String desc = "list item desc list item desc_";
    private List<String> urls;
    // urls长度
    private int len;
    private int page = 1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_listview);
        setTitle("recyclerView 实现listView效果");

        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
        // 下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                L.i("on refresh");
                refreshList();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        // 上拉加载更多
        // API 19
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            // 是否向上滑动
            private boolean isSlideingUp;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                // 不滑动
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastItemPosition = layoutManager.findLastCompletelyVisibleItemPosition();
                    int itemCount = layoutManager.getItemCount();

                    // 判断是否滑动到了最后一个item并且是向上滑动
                    if (lastItemPosition == (itemCount - 1) && isSlideingUp) {
                        loadMore();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                isSlideingUp = dy > 0;
            }
        });

    }

    // 加载下一页
    private void loadMore() {
        page ++;
        L.i("next page:" + page);
        getList();
    }

    // 刷新列表
    private void refreshList() {
        list.clear();
        page = 1;
        getList();
    }

    private void initData() {
        urls = ImageUrlConfig.getUrls();
        len = urls.size();
        getList();

    }

    // 获取item
    private void getList() {
        int last_item = page * 10;
        if (last_item < len) {
            for (int i = (page - 1) * 10; i < last_item; i++) {
                ListItem item = new ListItem();
                item.setTitle(title + i);
                item.setDesc(desc + i);
                item.setUrl(urls.get(i));
                list.add(item);
            }
            adapter.notifyDataSetChanged();
        }
    }

    private void initView() {
        swipeRefreshLayout = findViewById(R.id.refresh_layout);

        recyclerView = findViewById(R.id.rec_list);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        // 设置layoutManager
        recyclerView.setLayoutManager(linearLayoutManager);

        // 设置间距
        ListItemDecoration listItemDecoration = new ListItemDecoration(margin_bottom);
        recyclerView.addItemDecoration(listItemDecoration);

        // 设置adapter
        adapter = new RecAdapterForListView(this, list);
        recyclerView.setAdapter(adapter);
    }
}
