# recyclerView实现gridView布局

## layoutManager `GridLayoutManager`
- 设置footer_item横跨2格`onAttachedToRecyclerView`
```java
gridLayoutManager = new GridLayoutManager(this, 2);
recyclerView.setLayoutManager(gridLayoutManager);

// 设置间距
GridItemDecoration decoration = new GridItemDecoration(margin_bottom);
recyclerView.addItemDecoration(decoration);

```

## Adapter `RecyclerView.Adapter`
```java

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

```

### 下拉刷新 `swipeRefreshLayout`
```java
// 设置下拉刷新
refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
    @Override
    public void onRefresh() {
        L.i("onRefresh");
        refreshList();
    }
})
```

### 上拉加载更多
```java
// 上拉加载更多
// API 19
recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
   boolean isUpslide = false;
   @Override
   public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
       super.onScrollStateChanged(recyclerView, newState);
       GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
       // 滑动停止后
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

// API 23(android6.0)
// recyclerView.setOnScrollChangeListener

```