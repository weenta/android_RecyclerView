# recyclerView 实现listView效果

### LinearLayoutManager
```java
    // 构造一 参数为上下文环境，实现的是默认的垂直布局
    LinearLayoutManager layoutManager=new LinearLayoutManager(MainActivity.this);
    
    // 构造二 第一个参数为上下文环境，第二个参数为布局显示方式，第三个参数为布尔值是否反转
    new LinearLayoutManager( Context context, int orientation, boolean reverseLayout)
   
    LinearLayoutManager layoutManager1=new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false);
    recyclerView.setLayoutManager(layoutManager1);

```

#### 设置linerLayout布局间距
> `recyclerView.addItemDecoration(listItemDecoration);`
```
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
```

### adapter
```java
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
```

#### ViewHolder
```java
/**
 * holder1
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


/**
 * holder2
 * forListView itemFooter Holder
 */
public class HolderListViewFooter extends RecyclerView.ViewHolder {
    public ProgressBar progressBar;
    public HolderListViewFooter(View itemView) {
        super(itemView);
        progressBar = itemView.findViewById(R.id.progress_bar);
    }
}


```

### 下拉刷新 `swipeRefreshLayout`

### 上拉加载更多
```java
// 上拉加载更多
// API 19
recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
    // 是否向上滑动
    private boolean isSlideingUp;

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        // 滑动停止后
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
        // dy大于0 向上滑动，否则向下
        isSlideingUp = dy > 0;
    }
});

// API 23(android6.0)
// recyclerView.setOnScrollChangeListener

```