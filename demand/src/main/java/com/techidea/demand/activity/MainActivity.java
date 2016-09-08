package com.techidea.demand.activity;

import android.content.Intent;
import android.support.annotation.MenuRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.techidea.demand.R;
import com.techidea.demand.entity.BufferApi;
import com.techidea.demand.service.BufferApiRequest;

import java.util.UUID;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void setToolBar() {
        setToolbarTitle("HOME");
        setMenu(R.menu.menu_main, new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_menu:
                        break;
                    case R.id.action_order:
                        break;
                }
                return true;
            }
        });

        setOnNavigationClickListener(R.mipmap.logout, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void init() {

    }

    @OnClick(R.id.button_add)
    void buttonAdd() {
        String random = UUID.randomUUID().toString();
        BufferApi bufferApi = new BufferApi();
        bufferApi.setUrl("cn.bing.com" + random);
        bufferApi.setParams(random);
        BufferApiRequest.getInstance().addBufferApi(bufferApi);
    }

    @OnClick(R.id.material)
    void materialClick() {
        startActivity(new Intent(this, MaterialActivity.class));
    }

    @OnClick(R.id.load)
    void loadClick() {
        startActivity(new Intent(this, LoadDataActivity.class));
    }
}
