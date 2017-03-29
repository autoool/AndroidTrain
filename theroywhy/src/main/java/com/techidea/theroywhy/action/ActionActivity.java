package com.techidea.theroywhy.action;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.techidea.commonlibrary.widget.DialogHelper;
import com.techidea.theroywhy.R;
import com.techidea.theroywhy.customview.shape.AvatarActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zchao on 2017/3/8.
 */

public class ActionActivity extends AppCompatActivity {

    private static final String TAG = ActionActivity.class.getSimpleName();
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.button_action1)
    void action1() {
//        Intent intent = new Intent();
//        intent.setAction("cn.betatown.yunpos.simple.hand.annel.SETTING");
//        startActivity(intent);
  /*      Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClass(this, ApplicationActivity.class);
        getApplicationContext().startActivity(intent);*/

        startActivity(new Intent(this, AvatarActivity.class));

//        if (progressDialog == null) {
//            progressDialog = DialogHelper.getProgressDialog(this, "正在加载……");
//        }
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.show();

    }



    @OnClick(R.id.button_action2)
    void action2() {
        if (progressDialog == null)
            return;
        progressDialog.dismiss();
        progressDialog = null;

//        Intent intent = new Intent();
//        intent.setAction("cn.betatown.yunpos.simple.hand.annel.UNIONSETTING");
//        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume Test");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
