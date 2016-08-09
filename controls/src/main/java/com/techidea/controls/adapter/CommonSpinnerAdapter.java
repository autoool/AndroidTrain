package com.techidea.controls.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.techidea.controls.R;

import java.util.List;

/**
 * Created by Administrator on 2016/8/9.
 */
public class CommonSpinnerAdapter extends BaseAdapter {

    private List<SpinnerItem> mDatas;
    private int textColor;

    public CommonSpinnerAdapter(List<SpinnerItem> datas, int textColor) {
        this.mDatas = datas;
        this.textColor = textColor;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        HolderItem holderItem;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_spinner_item, null);
            textView = (TextView) convertView.findViewById(R.id.textview_spinner_item);
            holderItem = new HolderItem();
            holderItem.mTextViewName = textView;
            convertView.setTag(holderItem);
        } else {
            holderItem = (HolderItem) convertView.getTag();
            textView = holderItem.mTextViewName;
        }
        textView.setText(mDatas.get(position).getName());
        textView.setTextColor(ContextCompat.getColor(parent.getContext(), textColor));
        return convertView;
    }

    private static class HolderItem {
        public TextView mTextViewName;
    }
}
