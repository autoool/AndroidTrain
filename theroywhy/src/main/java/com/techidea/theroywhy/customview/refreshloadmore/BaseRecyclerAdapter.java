package com.techidea.theroywhy.customview.refreshloadmore;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.techidea.theroywhy.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zchao on 2017/3/6.
 */

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter {

    protected List<T> items;
    protected Context context;
    protected LayoutInflater layoutInflater;

    protected String systemTime;

    public static final int STATE_NO_MORE = 1;
    public static final int STATE_LOAD_MORE = 2;
    public static final int STATE_INVALID_NETWORK = 3;
    public static final int STATE_HIDE = 5;
    public static final int STATE_REFRESHING = 6;
    public static final int STATE_LOAD_ERROR = 7;
    public static final int STATE_LOADING = 8;

    public final int BEHAVIOR_MODE;
    protected int state;

    public static final int NEITHER = 0;
    public static final int ONLY_HEADER = 1;
    public static final int ONLY_FOOTER = 2;
    public static final int BOTH_HEADER_FOOTER = 3;

    public static final int VIEW_TYPE_NORMAL = 0;
    public static final int VIEW_TYPE_HEADER = -1;
    public static final int VIEW_TYPE_FOOTER = -2;

    private View.OnClickListener onClickListener;
    private View.OnLongClickListener onLongClickListener;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    private OnLoadingHeaderCallBack onLoadingHeaderCallBack;

    protected View headerView;

    public BaseRecyclerAdapter(Context context, int mode) {
        items = new ArrayList<>();
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        BEHAVIOR_MODE = mode;
        state = STATE_HIDE;
        initListener();
    }

    private void initListener() {
        onClickListener = new OnClickListener() {
            @Override
            public void onClick(int position, long itemId) {
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick(position, itemId);
            }
        };

        onLongClickListener = new OnLongClickListener() {
            @Override
            public boolean onLongClick(int position, long itemId) {
                if (onItemLongClickListener != null) {
                    onItemLongClickListener.onLongClick(position, itemId);
                    return true;
                }
                return false;
            }
        };
    }

    public void setSystemTime(String systemTime) {
        this.systemTime = systemTime;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_HEADER:
                if (onLoadingHeaderCallBack != null) {
                    return onLoadingHeaderCallBack.onCreateHeaderHolder(parent);
                } else {
                    throw new IllegalArgumentException("you have to impl the interface when using this viewType");
                }
            case VIEW_TYPE_FOOTER:
                return new FooterViewHolder(layoutInflater.inflate(R.layout.recycleriew_footer_view, parent, false));
            default:
                final RecyclerView.ViewHolder holder = onCreateDefaultViewHolder(parent, viewType);
                if (holder != null) {
                    holder.itemView.setTag(holder);
                    holder.itemView.setOnLongClickListener(onLongClickListener);
                    holder.itemView.setOnClickListener(onClickListener);
                }
                return holder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_HEADER:
                if (onLoadingHeaderCallBack != null) {
                    onLoadingHeaderCallBack.onBindHeaderHolder(holder, position);
                }
                break;
            case VIEW_TYPE_FOOTER:
                FooterViewHolder fvh = (FooterViewHolder) holder;
                fvh.itemView.setVisibility(View.VISIBLE);
                switch (state) {
                    case STATE_INVALID_NETWORK:
                        break;
                    case STATE_LOAD_MORE:
                    case STATE_LOADING:
                        fvh.tv_footer.setText("正在加载……");
                        fvh.pb_footer.setVisibility(View.VISIBLE);
                        break;
                    case STATE_NO_MORE:
                        fvh.tv_footer.setText("没有更多数据");
                        fvh.pb_footer.setVisibility(View.GONE);
                        break;
                    case STATE_REFRESHING:
                        fvh.tv_footer.setText("正在刷新");
                        fvh.pb_footer.setVisibility(View.GONE);
                        break;
                    case STATE_LOAD_ERROR:
                        fvh.tv_footer.setText("加载失败");
                        fvh.pb_footer.setVisibility(View.GONE);
                        break;
                    case STATE_HIDE:
                        fvh.itemView.setVisibility(View.GONE);
                        break;
                }
                break;
            default:
                onBindDefaultViewHolder(holder, getItems().get(getIndex(position)), position);
                break;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) manager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == VIEW_TYPE_HEADER
                            || getItemViewType(position) == VIEW_TYPE_FOOTER
                            ? gridLayoutManager.getSpanCount() : 1;
                }
            });
        }
    }

    /**
     * 当RecyclerView在windows活动时获取StaggeredGridLayoutManager布局管理器，修正header和footer显示整行
     *
     * @param holder
     */
    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            if (BEHAVIOR_MODE == ONLY_HEADER) {
                p.setFullSpan(holder.getLayoutPosition() == 0);
            } else if (BEHAVIOR_MODE == ONLY_FOOTER) {
                p.setFullSpan(holder.getLayoutPosition() == items.size() + 1);
            } else if (BEHAVIOR_MODE == BOTH_HEADER_FOOTER) {
                if (holder.getLayoutPosition() == 0 || holder.getLayoutPosition() == items.size() + 1)
                    p.setFullSpan(true);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && (BEHAVIOR_MODE == ONLY_HEADER
                || BEHAVIOR_MODE == BOTH_HEADER_FOOTER))
            return VIEW_TYPE_HEADER;
        if (position + 1 == getItemCount()
                && (BEHAVIOR_MODE == ONLY_FOOTER)
                || BEHAVIOR_MODE == BOTH_HEADER_FOOTER)
            return VIEW_TYPE_FOOTER;
        else return VIEW_TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        if (BEHAVIOR_MODE == ONLY_FOOTER || BEHAVIOR_MODE == ONLY_HEADER) {
            return items.size() + 1;
        } else if (BEHAVIOR_MODE == BOTH_HEADER_FOOTER) {
            return items.size() + 2;
        } else
            return items.size();
    }

    public int getCount() {
        return items.size();
    }

    public final List<T> getItems() {
        return items;
    }

    public View getHeaderView() {
        return this.headerView;
    }

    public void setHeaderView(View headerView) {
        this.headerView = headerView;
    }

    public void addAll(List<T> items) {
        if (items != null) {
            this.items.addAll(items);
            notifyItemRangeInserted(this.items.size(), items.size());
        }
    }

    public final void addItem(T item) {
        if (item != null) {
            this.items.add(item);
            notifyItemChanged(items.size());
        }
    }

    public void addItem(int position, T item) {
        if (item != null) {
            this.items.add(position, item);
            notifyItemChanged(position);
        }
    }

    public void replaceItem(int position, T item) {
        if (item != null) {
            this.items.set(getIndex(position), item);
            notifyItemChanged(position);
        }
    }

    public void updateItetm(int position) {
        if (getItemCount() > position) {
            notifyItemChanged(position);
        }
    }

    public final void removeItem(T item) {
        if (this.items.contains(item)) {
            int position = items.indexOf(item);
            this.items.remove(item);
            notifyItemRemoved(position);
        }
    }

    public final T getItem(int position) {
        int p = getIndex(position);
        if (p < 0 || p >= items.size()) {
            return null;
        }
        return items.get(getIndex(position));
    }

    public final void resetItem(List<T> items) {
        if (items != null) {
            clear();
            addAll(items);
        }
    }

    public final void clear() {
        this.items.clear();
        setState(STATE_HIDE, false);
        notifyDataSetChanged();
    }

    public void setState(int state, boolean update) {
        this.state = state;
        if (update)
            updateItetm(getItemCount() - 1);
    }

    public int getState() {
        return state;
    }

    protected int getIndex(int position) {
        return BEHAVIOR_MODE == ONLY_HEADER
                || BEHAVIOR_MODE == BOTH_HEADER_FOOTER ? position - 1 : position;
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public final void setOnLoadingHeaderCallBack(OnLoadingHeaderCallBack onLoadingHeaderCallBack) {
        this.onLoadingHeaderCallBack = onLoadingHeaderCallBack;
    }


    protected abstract RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type);

    protected abstract void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, T item, int position);

    /**
     * 可以共用同一个listener，相对高效
     */
    public static abstract class OnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) v.getTag();
            onClick(holder.getAdapterPosition(), holder.getItemId());
        }

        public abstract void onClick(int position, long itemId);
    }


    /**
     * 可以共用同一个listener，相对高效
     */
    public static abstract class OnLongClickListener implements View.OnLongClickListener {
        @Override
        public boolean onLongClick(View v) {
            RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) v.getTag();
            return onLongClick(holder.getAdapterPosition(), holder.getItemId());
        }

        public abstract boolean onLongClick(int position, long itemId);
    }


    public interface OnItemClickListener {
        void onItemClick(int position, long itetmId);
    }

    public interface OnItemLongClickListener {
        void onLongClick(int position, long itemId);
    }

    /**
     * for load header view
     */
    public interface OnLoadingHeaderCallBack {
        RecyclerView.ViewHolder onCreateHeaderHolder(ViewGroup parent);

        void onBindHeaderHolder(RecyclerView.ViewHolder holder, int position);
    }


    public static class FooterViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar pb_footer;
        public TextView tv_footer;

        public FooterViewHolder(View view) {
            super(view);
            pb_footer = (ProgressBar) view.findViewById(R.id.pb_footer);
            tv_footer = (TextView) view.findViewById(R.id.tv_footer);
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

}
