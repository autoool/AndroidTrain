package com.techidea.theroywhy.action;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.techidea.theroywhy.R;
import com.techidea.theroywhy.view.activity.ToolbarActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zchao on 2017/3/8.
 * Android View 事件分发机制 源码解析 （上）
 * http://blog.csdn.net/lmj623565791/article/details/38960443
 * Android ViewGroup事件分发机制
 * http://blog.csdn.net/lmj623565791/article/details/39102591
 */

public class ActionActivity extends AppCompatActivity {

    @Bind(R.id.button_action1)
    Button buttonAction1;
    @Bind(R.id.imageview_click)
    ImageView imageViewClick;
    @Bind(R.id.mybutton01)
    MyButton myButton01;

    private static final String TAG = ActionActivity.class.getSimpleName();
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);
        ButterKnife.bind(this);
        buttonAction1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "buttonAction1 onClick");
            }
        });

        buttonAction1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(TAG, "buttonAction1 onTouch" + event.getAction());
                return true;
            }
        });

        imageViewClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "imageViewClick onClick");
            }
        });

        imageViewClick.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(TAG, "imageViewClick onTouch" + event.getAction());
                return false;
            }
        });

        //事件执行
        myButton01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick");
            }
        });

        //当该方法返回true,onClick不执行
        myButton01.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        Log.d(TAG, "onTouch ACTION_DOWN");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.d(TAG, "onTouch ACTION_UP");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.d(TAG, "onTouch ACTION_MOVE");
                        break;
                }
                return false;
            }
        });

        //长按当返回true时，onClick方法不执行
        myButton01.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d(TAG, "onLongClick");

                return true;
            }
        });


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

//        startActivity(new Intent(this, AvatarActivity.class));

//        if (progressDialog == null) {
//            progressDialog = DialogHelper.getProgressDialog(this, "正在加载……");
//        }
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.show();
        startActivity(new Intent(this, ToolbarActivity.class));

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
