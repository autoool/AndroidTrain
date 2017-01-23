package com.techidea.demand.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.techidea.commonlibrary.adapter.CommonAdapter;
import com.techidea.commonlibrary.adapter.CommonViewHolder;
import com.techidea.demand.R;
import com.techidea.demand.entity.Student;

import java.util.List;

/**
 * Created by zchao on 2016/11/17.
 */

public class StudentInfoAdapter extends CommonAdapter<Student> {

    private List<Student> datas;
    private Context context;

    public StudentInfoAdapter(Context context, List<Student> datas, int itemLayoutId) {
        super(context, datas, itemLayoutId);
        this.context = context;
        this.datas = datas;
    }

    @Override
    public void convert(CommonViewHolder viewHolder, Student item) {
        viewHolder.setText(R.id.textview_name, item.getName());
        ImageView imageView = (ImageView) viewHolder.getView(R.id.imageview_portrait);
        if (item.getImageUrl() != null) {
            Glide.with(context)
                    .load(item.getImageUrl())
                    .centerCrop()
                    .error(R.mipmap.facebook_blue)
                    .placeholder(R.mipmap.facebook_blue)
                    .crossFade()
                    .into(imageView);
        }
    }

    public void delete(int position) {
        datas.remove(position);
        notifyDataSetChanged();
    }
}
