package com.techidea.demand.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.techidea.demand.R;
import com.techidea.demand.common.Contast;

import butterknife.Bind;


/**
 * Created by zchao on 2016/11/29.
 */

public class ImageEditActivity extends BaseActivity {

    @Bind(R.id.imageview_edit)
    ImageView imageViewEdit;

    private String imagePath;
    private String from ="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageedit);
    }

    @Override
    public void setToolBar() {
        from = getIntent().getStringExtra(Contast.PUT_EXTRA_FROM);
        if (from.equals("main")) {
            setMenu(R.menu.menu_image_edit, new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.action_delete:
                            deleteImage();
                            break;
                    }
                    return true;
                }
            });
        } else {
            setMenu(R.menu.menu_image_done, new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.action_done:
                            selectImage();
                            break;
                    }
                    return true;
                }
            });
        }

        setOnNavigationClickListener(R.mipmap.back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void init() {
        imagePath = getIntent().getStringExtra(Contast.PUT_EXTRA_PATH);
        Glide.with(this).load(imagePath).into(imageViewEdit);
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.putExtra(Contast.PUT_EXTRA_OPERATOR,Contast.PUT_EXTRA_ADD);
        intent.putExtra(Contast.PUT_EXTRA_PATH, imagePath);
        intent.setClass(this, UploadImagesActivity.class);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void deleteImage(){
        Intent intent = new Intent();
        intent.putExtra(Contast.PUT_EXTRA_OPERATOR,Contast.PUT_EXTRA_DELETE);
        intent.putExtra(Contast.PUT_EXTRA_PATH, imagePath);
        intent.setClass(this, UploadImagesActivity.class);
        setResult(RESULT_OK, intent);
        finish();
    }
}
