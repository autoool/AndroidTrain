package com.techidea.demand.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;

import com.techidea.demand.R;
import com.techidea.demand.adapter.ImageListAdapter;
import com.techidea.demand.adapter.ImageRecyclerAdapter;
import com.techidea.demand.entity.UploadImage;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by zchao on 2017/3/14.
 */

public class RefreshActivity extends AppCompatActivity {

    @Bind(R.id.store_house_ptr_frame)
    PtrClassicFrameLayout ptrFrameLayout;
//    @Bind(R.id.listiewimage)
//    ListView listViewImages;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerView;

    private ImageListAdapter imagesAdapter;
    private ImageRecyclerAdapter imageRecyclerAdapter;
    private List<UploadImage> imageList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptrframe);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        imageList = new ArrayList<>();
        UploadImage uploadImage = new UploadImage();
        //http://image.baidu.com/channel/listjson?pn=1&rn=30&tag1=%E7%BE%8E%E5%A5%B3&tag2=%E5%85%A8%E9%83%A8&ie=utf8
        uploadImage.setUrl("http://img.hb.aicdn.com/59326ff7234f7ea295bb6e8938e8e0755f7a804a6445-IY6kp5_fw658");
        imageList.clear();
        for (int i = 0; i < 30; i++) {
            imageList.add(uploadImage);
        }
//        imagesAdapter = new ImageListAdapter(this, imageList, R.layout.item_upload_image);
//        listViewImages.setAdapter(imagesAdapter);
        imageRecyclerAdapter = new ImageRecyclerAdapter(recyclerView, imageList, R.layout.item_upload_image);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(imageRecyclerAdapter);
    }

    private void initView() {
        ptrFrameLayout.setLastUpdateTimeRelateObject(this);
        ptrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                updateData();
            }
        });
        ptrFrameLayout.setResistance(1.7f);
        ptrFrameLayout.setRatioOfHeaderHeightToRefresh(1.2f);
        ptrFrameLayout.setDurationToClose(200);
        ptrFrameLayout.setDurationToCloseHeader(1000);
        // default is false
        ptrFrameLayout.setPullToRefresh(true);
        // default is true
        ptrFrameLayout.setKeepHeaderWhenRefresh(true);
        ptrFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                ptrFrameLayout.autoRefresh();
            }
        }, 100);
    }


    private void updateData() {
//        imagesAdapter.getImageList().clear();
        imageRecyclerAdapter.getImageList().clear();
        UploadImage uploadImage = new UploadImage();
        //http://image.baidu.com/channel/listjson?pn=1&rn=30&tag1=%E7%BE%8E%E5%A5%B3&tag2=%E5%85%A8%E9%83%A8&ie=utf8
        uploadImage.setUrl("http://img.hb.aicdn.com/59326ff7234f7ea295bb6e8938e8e0755f7a804a6445-IY6kp5_fw658");
        imageList.clear();
        for (int i = 0; i < 5; i++) {
            imageList.add(uploadImage);
        }
//        imagesAdapter.getImageList().addAll(imageList);
//        ptrFrameLayout.refreshComplete();
//        imagesAdapter.notifyDataSetChanged();

        imageRecyclerAdapter.getImageList().addAll(imageList);
        ptrFrameLayout.refreshComplete();
        imageRecyclerAdapter.notifyDataSetChanged();
    }

}
