package com.techidea.controls.adapter;

import android.support.v7.widget.RecyclerView;

import com.techidea.commonlibrary.adapter.BaseRecyclerAdapter;
import com.techidea.commonlibrary.adapter.BaseRecyclerHolder;
import com.techidea.controls.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2016/8/11.
 */
public class RadioListAdapter extends BaseRecyclerAdapter<RadioItem> {

    private List<RadioItem> mDatas;

    public RadioListAdapter(RecyclerView v, Collection<RadioItem> datas, int itemLayoutId) {
        super(v, datas, itemLayoutId);
        this.mDatas = new ArrayList<>(datas);
    }

    @Override
    public void convert(BaseRecyclerHolder holder, RadioItem item, int position, boolean isScrolling) {
        holder.setText(R.id.textview_radio_item, item.getContent());
        holder.setRadioButtonChecked(R.id.radiobutton_item, item.isSelect());
    }

    public void changeItemChecked(int position) {
        for (RadioItem item : mDatas) {
            item.setSelect(false);
        }
        mDatas.get(position).setSelect(true);
        notifyDataSetChanged();
    }
}
