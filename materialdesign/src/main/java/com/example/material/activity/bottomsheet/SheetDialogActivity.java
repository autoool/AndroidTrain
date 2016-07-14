package com.example.material.activity.bottomsheet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.material.R;
import com.example.material.adapter.ImageItem;
import com.example.material.adapter.MenuSheetDialogAdapter;
import com.techidea.commonlibrary.adapter.BaseRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zchao on 2016/7/14.
 */
public class SheetDialogActivity extends AppCompatActivity {


    @Bind(R.id.textview_bottom_sheet)
    TextView mTextViewBottomSheet;

    private MenuSheetDialogAdapter mMenuSheetDialogAdapter;
    private List<ImageItem> mImageItems;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheetdialog);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initialize();
        mTextViewBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialog();
            }
        });
    }

    private void initialize() {
        mImageItems = new ArrayList<>();
        mImageItems.add(new ImageItem("Weixin", "", R.drawable.weixin));
        mImageItems.add(new ImageItem("Douban", "", R.drawable.douban));
        mImageItems.add(new ImageItem("facebook", "", R.drawable.facebook));
        mImageItems.add(new ImageItem("google", "", R.drawable.google));
        mImageItems.add(new ImageItem("hangouts", "", R.drawable.hangouts));
        mImageItems.add(new ImageItem("mail", "", R.drawable.mail));
        mImageItems.add(new ImageItem("phone", "", R.drawable.phone));
        mImageItems.add(new ImageItem("qq", "", R.drawable.qq));
        mImageItems.add(new ImageItem("message", "", R.drawable.message));
    }

    private void showBottomSheetDialog() {
        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_menusheet, null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMenuSheetDialogAdapter = new MenuSheetDialogAdapter(recyclerView, mImageItems, R.layout.view_menusheet_dialog_item);
        mMenuSheetDialogAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object data, int position) {
                dialog.dismiss();
            }
        });
        recyclerView.setAdapter(mMenuSheetDialogAdapter);
        dialog.setContentView(view);
        dialog.show();
    }
}
