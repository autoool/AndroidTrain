package com.techidea.theroywhy.customview.banner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.techidea.theroywhy.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zchao on 2017/3/3.
 */

public class BannerActivity extends AppCompatActivity {

    @Bind(R.id.content_banner)
    RelativeLayout layoutBanner;

    private EventHeaderView eventHeaderView;
    private RequestManager requestManager;
    private List<Banner> banners;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        ButterKnife.bind(this);
        banners = new ArrayList<>();
        initData();
        requestManager = Glide.with(this);
        eventHeaderView = new EventHeaderView(this, requestManager);
        eventHeaderView.setBanners(banners);
        layoutBanner.addView(eventHeaderView);
    }

    private void initData() {
        Banner banner = new Banner();
        Banner banner1 = new Banner();
        Banner banner2 = new Banner();
        Banner banner3 = new Banner();
        banner.setImg("http://a3.topitme.com/1/fa/47/1167879370a2a47fa1l.jpg");
        banner1.setImg("http://a3.topitme.com/9/88/b3/1119333037c90b3889l.jpg");
        banner2.setImg("http://a4.topitme.com/l046/1004699252c936a89a.jpg");
        banner3.setImg("http://a4.topitme.com/l/201101/16/12951809577884.jpg");
        banners.add(banner);
        banners.add(banner1);
        banners.add(banner2);
        banners.add(banner3);
    }
}
