package com.techidea.demand.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.techidea.demand.R;
import com.techidea.demand.adapter.ImageListAdapter;
import com.techidea.demand.adapter.ImageRecyclerAdapter;
import com.techidea.demand.entity.UploadImage;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.util.LocalDisplay;
import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by zchao on 2017/3/14.
 */

public class RefreshLoadMoreAcivity extends AppCompatActivity {

    @Bind(R.id.ptrlayout)
    PtrClassicFrameLayout ptrLayout;
    @Bind(R.id.loadmore_container)
    LoadMoreListViewContainer loadMoreListViewContainer;
    @Bind(R.id.listview)
    ListView listView;

    private ImageListAdapter imageListAdapter;
    private List<UploadImage> uploadImageList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadmore);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        uploadImageList = new ArrayList<>();
    }

    private void initView() {
        ptrLayout.setLastUpdateTimeRelateObject(this);
        ptrLayout.setLoadingMinTime(1000);
        ptrLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, listView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getFirstPage();
            }
        });

        View headerView = new View(this);
        headerView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                LocalDisplay.dp2px(20)));
        listView.addHeaderView(headerView);
        loadMoreListViewContainer.useDefaultHeader();
        imageListAdapter = new ImageListAdapter(this, uploadImageList, R.layout.item_upload_image);
        listView.setAdapter(imageListAdapter);

        loadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                getNextPage();
            }
        });

        ptrLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                ptrLayout.autoRefresh(false);
            }
        }, 150);
    }

    private void getFirstPage() {
        uploadImageList.clear();
        imageListAdapter.getImageList().clear();
        UploadImage uploadImage = new UploadImage();
        uploadImage.setUrl("http://a4.topitme.com/l/201009/29/12857488431166.jpg");
        for (int i = 0; i < 5; i++) {
            uploadImageList.add(uploadImage);
        }
        imageListAdapter.getImageList().addAll(uploadImageList);
        ptrLayout.refreshComplete();
        loadMoreListViewContainer.loadMoreFinish(false, true);
        imageListAdapter.notifyDataSetChanged();
    }

    private void getNextPage() {
        List<UploadImage> uploadImages = new ArrayList<>();
        UploadImage uploadImage = new UploadImage();
        uploadImage.setUrl("http://a4.topitme.com/l/201103/27/13012031006116.jpg");
        for (int i = 0; i < 5; i++) {
            uploadImages.add(uploadImage);
        }
        imageListAdapter.getImageList().addAll(uploadImages);
        ptrLayout.refreshComplete();
        loadMoreListViewContainer.loadMoreFinish(false, false);
        imageListAdapter.notifyDataSetChanged();
    }
}
