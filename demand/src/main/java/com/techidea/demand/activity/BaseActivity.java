package com.techidea.demand.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.MenuRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.techidea.demand.R;

import butterknife.ButterKnife;

/**
 * Created by zchao on 2016/9/8.
 * http://sanjay-f.github.io/2016/01/17/%E5%85%B3%E4%BA%8EBaseActivity%E7%9A%84%E6%9C%80%E4%BD%B3%E5%AE%9E%E8%B7%B5/
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected String TAG = this.getClass().getSimpleName();
    private static final int INVALID_MENU = -1;

    private LinearLayout contentView = null;
    protected Context context = null;
    private Toolbar toolbar;
    private TextView toolbarTitle;
    private Toolbar.OnMenuItemClickListener onMenuItemClickListener;
    private int menuRes = INVALID_MENU;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        context = this;
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        if (contentView == null && R.layout.activity_base == layoutResID) {
            super.setContentView(R.layout.activity_base);
            contentView = (LinearLayout) findViewById(R.id.layout_center);
            toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
            contentView.removeAllViews();
        } else if (layoutResID != R.layout.activity_base) {
            View centerView = LayoutInflater.from(this).inflate(layoutResID, null);
            contentView.addView(centerView);
            beforeSetToolbar();
            setToolBar();
            afterSetToolbar();
            ButterKnife.bind(this);
            init();
        }
    }


    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    public void beforeSetToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("");
        toolbar.setEnabled(true);
    }

    private void afterSetToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setOnMenuItemClickListener(onMenuItemClickListener);
    }

    public abstract void setToolBar();
    public abstract void init();

    public void setMenuId(@MenuRes int menuRes) {
        this.menuRes = menuRes;
    }

    public void setMenu(@MenuRes int menuRes, Toolbar.OnMenuItemClickListener onMenuItemClickListener) {
        this.menuRes = menuRes;
        setOnMenuItemClickListener(onMenuItemClickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (menuRes != INVALID_MENU) {
            getMenuInflater().inflate(menuRes, menu);
        }
        return true;
    }

    public void setToolbarTitle(String title) {
        toolbarTitle.setText(title);
    }

    public void setToolbarTitle(@StringRes int resId) {
        toolbarTitle.setText(resId);
    }


    public void setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener onMenuItemClickListener) {
        this.onMenuItemClickListener = onMenuItemClickListener;
    }

    public void setOnNavigationClickListener(@DrawableRes int redId, View.OnClickListener listener) {
        toolbar.setNavigationIcon(redId);
        toolbar.setNavigationOnClickListener(listener);
    }
}
