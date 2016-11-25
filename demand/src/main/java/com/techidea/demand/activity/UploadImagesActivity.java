package com.techidea.demand.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.techidea.demand.R;
import com.techidea.demand.adapter.ImagesAdapter;
import com.techidea.demand.entity.UploadImages;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/11/25.
 */

public class UploadImagesActivity extends AppCompatActivity {

    @Bind(R.id.edittext_content)
    EditText editTextContent;
    @Bind(R.id.recyclerview_images)
    RecyclerView recyclerView;
    @Bind(R.id.button_submit)
    Button buttonSubmit;

    ImagesAdapter imagesAdapter;
    private List<UploadImages> imagesList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadimages);
        ButterKnife.bind(this);
        imagesList = new ArrayList<>();
        imagesAdapter = new ImagesAdapter(recyclerView, imagesList, R.layout.item_upload_image);
    }

    @OnClick(R.id.button_submit)
    void submitOnClick() {
        String filepath = "";
        filepath += Environment.getDataDirectory() + "\n";
        filepath += Environment.getExternalStorageDirectory() + "\n";
        filepath += Environment.getDownloadCacheDirectory() + "\n";
        filepath += Environment.getRootDirectory() + "\n";
        filepath+=getFilesDir().getAbsolutePath()+ "\n";
        filepath+=getCacheDir();

        Log.d("FILEPATH", filepath);
        System.out.println(filepath);
    }

    private void initImages() {
        imagesList.add(new UploadImages("", -1, "", "", null));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
