# RecyclerView实现waterfall布局
`implementation 'com.android.support:design:27.1.1'`

## layoutManager 
```java
layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
recyclerView.setLayoutManager(layoutManager);

```


## adapter
`RecyclerView.Adapter`
```java

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


```
## ViewHolder
` RecyclerView.ViewHolder`
```java

public class HolderWaterfall extends RecyclerView.ViewHolder {
    public ImageView hImageView;

    public HolderWaterfall(View itemView) {
        super(itemView);
        hImageView = itemView.findViewById(R.id.img_waterfall_item);
    }
}

```
## ItemDecoration
`recyclerView.addItemDecoration(decoration);`
```java
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
```

## 上拉加载更多
```java
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
           // 获取最后的item
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

```

## 下拉刷新列表
`swipeRefreshLayout.setOnRefreshListener`
```java
swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
   @Override
   public void onRefresh() {
       list.clear();
       page = 1;
       getList();
       swipeRefreshLayout.setRefreshing(false);
   }
});

```