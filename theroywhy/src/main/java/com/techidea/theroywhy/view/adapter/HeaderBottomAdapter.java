package com.techidea.theroywhy.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techidea.theroywhy.R;

/**
 * Created by zchao on 2017/2/28.
 */

public class HeaderBottomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int ITEM_TYPE_HEADER = 0;
    public static final int ITEM_TYPE_CONTENT = 1;
    public static final int ITEM_TYPE_BOTTOM = 2;

    public String[] texts = {"java", "python", "C++", "Php", ".NET", "js", "Ruby", "Swift", "OC"};

    private LayoutInflater layoutInflater;
    private Context context;
    private int headerCount = 1;//头部View个数
    private int bottomCount = 1;//底部View个数

    public HeaderBottomAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public int getContentItemCount() {
        return texts.length;
    }

    public boolean isHeaderView(int position) {
        return headerCount != 0 && position < headerCount;
    }

    public boolean isBottomView(int position) {
        return bottomCount != 0 && position >= (headerCount + getContentItemCount());
    }

    @Override
    public int getItemCount() {
        return headerCount + getContentItemCount() + bottomCount;
    }

    @Override
    public int getItemViewType(int position) {
        int dataItemCount = getContentItemCount();
        if (headerCount != 0 && position < headerCount)
            return ITEM_TYPE_HEADER;
        else if (bottomCount != 0 && position >= (headerCount + dataItemCount))
            return ITEM_TYPE_BOTTOM;
        else
            return ITEM_TYPE_CONTENT;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_HEADER) {
            return new HeaderViewHolder(layoutInflater.inflate(R.layout.rv_header, parent, false));
        } else if (viewType == ITEM_TYPE_CONTENT) {
            return new ContentViewHolder(layoutInflater.inflate(R.layout.view_item, parent, false));
        } else if (viewType == ITEM_TYPE_BOTTOM) {
            return new BottomViewHolder(layoutInflater.inflate(R.layout.rv_footer, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {

        } else if (holder instanceof ContentViewHolder) {
            ((ContentViewHolder) holder).textView.setText(texts[position - headerCount]);
        } else if (holder instanceof BottomViewHolder) {

        }
    }

    public static class ContentViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public ContentViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textview_content);
        }
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }


    public static class BottomViewHolder extends RecyclerView.ViewHolder {
        public BottomViewHolder(View itemView) {
            super(itemView);
        }
    }
}
