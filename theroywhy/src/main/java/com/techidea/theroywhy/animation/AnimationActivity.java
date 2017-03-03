package com.techidea.theroywhy.animation;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.techidea.theroywhy.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zchao on 2017/2/10.
 */

public class AnimationActivity extends AppCompatActivity {

    @Bind(R.id.imageview_anim1)
    ImageView imageViewAnim1;
    @Bind(R.id.imageview_scale)
    ImageView imageViewScale;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_rotate);
        animation.setFillAfter(true);
        imageViewAnim1.startAnimation(animation);

        imageViewScale.setImageResource(R.drawable.frame_anim);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageViewScale.getDrawable();
        animationDrawable.start();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {

            }
        });
    }
}
