package com.techidea.demand.itemadapter;

import android.content.Context;

import com.techidea.commonlibrary.adapter.CommonAdapter;
import com.techidea.commonlibrary.adapter.CommonViewHolder;
import com.techidea.demand.R;

import java.util.List;

/**
 * Created by zchao on 2016/11/17.
 */

public class StudentAdapter extends CommonAdapter<Student> {

    private List<Student> datas;

    public StudentAdapter(Context context, List<Student> datas, int itemLayoutId) {
        super(context, datas, itemLayoutId);
        this.datas = datas;
    }

    @Override
    public void convert(CommonViewHolder viewHolder, Student item) {
        viewHolder.setText(R.id.textview_name, item.getName());
    }

    public void delete(int position) {
        datas.remove(position);
    }
}
