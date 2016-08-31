package com.techidea.demand.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.techidea.demand.R;
import com.techidea.demand.entity.BufferApi;
import com.techidea.demand.service.BufferApiRequest;

import java.util.UUID;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_add)
    void buttonAdd() {
        String random = UUID.randomUUID().toString();
        BufferApi bufferApi = new BufferApi();
        bufferApi.setUrl("cn.bing.com" + random);
        bufferApi.setParams(random);
        BufferApiRequest.getInstance().addBufferApi(bufferApi);
    }
}
