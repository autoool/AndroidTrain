package com.techidea.demand.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.techidea.commonlibrary.adapter.BaseRecyclerAdapter;
import com.techidea.commonlibrary.adapter.DividerGridItemDecoration;
import com.techidea.demand.R;
import com.techidea.demand.adapter.ImagesAdapter;
import com.techidea.demand.common.Contast;
import com.techidea.demand.entity.UploadImage;
import com.techidea.demand.util.FileUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/11/25.
 */

public class UploadImagesActivity extends BaseActivity {

    private static final int RESULT_IMAGE_EDIT = 701;
    private static final int RESULT_TAKE_CAPTURE = 702;

    @Bind(R.id.edittext_content)
    EditText editTextContent;
    @Bind(R.id.recyclerview_images)
    RecyclerView recyclerView;
    @Bind(R.id.button_submit)
    Button buttonSubmit;

    ImagesAdapter imagesAdapter;
    private List<UploadImage> imagesList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadimages);
    }

    @Override
    public void setToolBar() {

    }

    @Override
    public void init() {
        imagesList = new ArrayList<>();
        initImages();

        imagesAdapter = new ImagesAdapter(recyclerView, imagesList, R.layout.item_upload_image);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        recyclerView.setAdapter(imagesAdapter);

        imagesAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object data, int position) {
                UploadImage uploadImage = (UploadImage) data;
                if (uploadImage.getTag() == -1) {
                    gotoCamera();
                } else {
                    gotoEdit(uploadImage.getFilePath(), "main");
                }
            }
        });
    }

    @OnClick(R.id.button_submit)
    void submitOnClick() {
        String filepath = "";
        filepath += Environment.getDataDirectory() + "\n";
        filepath += Environment.getExternalStorageDirectory() + "\n";
        filepath += Environment.getDownloadCacheDirectory() + "\n";
        filepath += Environment.getRootDirectory() + "\n";
        filepath += getFilesDir().getAbsolutePath() + "\n";
        filepath += getCacheDir();

        Log.d("FILEPATH", filepath);
        System.out.println(filepath);
    }

    private void initImages() {
        imagesList.add(new UploadImage(1, R.mipmap.facebook_blue));
        imagesList.add(new UploadImage(1, R.mipmap.facebook_green));
        imagesList.add(new UploadImage(1, R.mipmap.facebook_orange));
        imagesList.add(new UploadImage(1, R.mipmap.facebook_purple));
        imagesList.add(getImageAddTag());
    }

    private void gotoCamera() {
        if (imagesList.size() >= 7) {
            Toast.makeText(getApplicationContext(), "最多只能选择7张图片", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, RESULT_TAKE_CAPTURE);
        }
    }


    private void gotoEdit(String filePath, String from) {
        Intent intent = new Intent();
        intent.putExtra(Contast.PUT_EXTRA_FROM, from);
        intent.putExtra(Contast.PUT_EXTRA_PATH, filePath);
        intent.setClass(this, ImageEditActivity.class);
        startActivityForResult(intent, RESULT_IMAGE_EDIT);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK || data == null)
            return;
        switch (requestCode) {
            case RESULT_IMAGE_EDIT:
                String operator = data.getStringExtra(Contast.PUT_EXTRA_OPERATOR);
                String editpath = data.getStringExtra(Contast.PUT_EXTRA_PATH);
                updateImages(editpath, operator);
                break;
            case RESULT_TAKE_CAPTURE:
                String filename = "FG" + String.valueOf(System.currentTimeMillis());
                Bitmap bm = (Bitmap) data.getExtras().get(Contast.PUT_EXTRA_DATA);
                String path = FileUtils.saveBitmap(bm, filename);
                gotoEdit(path, "edit");
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void updateImages(String path, String operator) {
        List<UploadImage> list = new ArrayList<>();
        if (operator.equals(Contast.PUT_EXTRA_ADD)) {
            UploadImage uploadImage = new UploadImage();
            uploadImage.setFilePath(path);
            for (UploadImage image : imagesList) {
                if (image.getTag() == -1)
                    continue;
                list.add(image);
            }
            list.add(uploadImage);
            list.add(getImageAddTag());

        } else if (operator.equals(Contast.PUT_EXTRA_DELETE)) {
            FileUtils.delFile(path);
            for (UploadImage image : imagesList) {
                if (image.getFilePath().equals(path))
                    continue;
                list.add(image);
            }
        }
        imagesList = list;
        imagesAdapter.refresh(imagesList);
    }

    private UploadImage getImageAddTag() {
        return new UploadImage("", -1, "", "", null, 0);
    }

}
