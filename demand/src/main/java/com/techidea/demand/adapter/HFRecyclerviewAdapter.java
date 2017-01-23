package com.techidea.demand.adapter;

import android.content.ClipData;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.techidea.demand.R;
import com.techidea.demand.entity.Student;

import java.util.List;

/**
 * Created by zchao on 2017/1/23.
 */

public class HFRecyclerviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    List<Student> studentList;
    private Context context;

    public HFRecyclerviewAdapter(List<Student> studentList) {
        this.studentList = studentList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.header_item, parent, false);
            return new HeadHolder(v);
        } else if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.itemview_student, parent, false);
            return new ItemHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeadHolder) {
            HeadHolder headHolder = (HeadHolder) holder;
            headHolder.textView.setText("Head");
        } else if (holder instanceof ItemHolder) {
            Student student = getItem(position);
            ItemHolder itemHolder = (ItemHolder) holder;
            if (student != null) {
                itemHolder.textView.setText(student.getName());
                Glide.with(context)
                        .load(student.getImageUrl())
                        .centerCrop()
                        .error(R.mipmap.facebook_blue)
                        .placeholder(R.mipmap.facebook_blue)
                        .crossFade()
                        .into(itemHolder.imageView);
            }
        }
    }

    private Student getItem(int posotion) {
        if (posotion > 0 && posotion < studentList.size()) {
            return studentList.get(posotion);
        } else {
            return null;
        }
    }

    private boolean isHeader(int position) {
        return position == 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeader(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return studentList == null ? 1 : studentList.size() + 1;
    }

    class HeadHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public HeadHolder(View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.textview_header);
        }
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        public ItemHolder(View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.textview_name);
            this.imageView = (ImageView) itemView.findViewById(R.id.imageview_portrait);
        }
    }


}
