package com.techidea.controls.activity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.SeekBar;

import com.techidea.controls.R;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/7/4.
 */

public class VedioActivity extends AppCompatActivity {

    private static final String TAG = VedioActivity.class.getSimpleName();

    //    @Bind(R.id.videoview_one)
//    VideoView videoViewOne;
    @Bind(R.id.surfaceview_vedio)
    SurfaceView surfaceViewVedio;
    @Bind(R.id.button_play_start)
    Button buttonPlayStart;
    @Bind(R.id.seekbar_vedio)
    SeekBar seekBarVedio;

    private MediaPlayer mediaPlayer;
    private int position = 0;
    private boolean seekBarIsChange = false;
    private Handler handler = new Handler();
    private Runnable updatePlayProgressRunnable = new Runnable() {
        @Override
        public void run() {
            if (seekBarIsChange) {
                return;
            }
            seekBarVedio.setProgress(mediaPlayer.getCurrentPosition());
            handler.postDelayed(updatePlayProgressRunnable, 100);
        }
    };

    String vedio_url = "https://html5demos.com/assets/dizzy.mp4";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_vedio);
        ButterKnife.bind(this);
        initView();
//        initMediaPlayer();
    }

    private void playIntent() {
//        Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/Test_Movie.m4v");\
        Uri uri = Uri.parse(vedio_url);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Log.v("URI:::::::::", uri.toString());
        intent.setDataAndType(uri, "video/*");
        startActivity(intent);
    }

    private void initView() {
        mediaPlayer = new MediaPlayer();
        surfaceViewVedio.getHolder().setKeepScreenOn(true);
        surfaceViewVedio.getHolder().addCallback(new SurfaceViewCallback());
        surfaceViewVedio.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        setPlayStartTag();
        seekBarVedio.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                seekBarIsChange = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBarIsChange = false;
            }
        });
    }

    private void initMediaPlayer() {
        try {
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(vedio_url);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Log.d(TAG, "prepared");
                    seekBarVedio.setMax(mediaPlayer.getDuration());
                    mediaPlayer.start();
                    setPlayStopTag();
                    handler.postDelayed(updatePlayProgressRunnable, 100);
                }
            });
            mediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                @Override
                public void onSeekComplete(MediaPlayer mp) {
                    Log.d(TAG, "mediaplayer onSeekComplete");
                }
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    Log.d(TAG, "onError");
                    mediaPlayer.reset();
                    return false;
                }
            });
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @OnClick(R.id.button_play_start)
    void startClick() {
        if (buttonPlayStart.getTag().equals("start")) {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.setDisplay(surfaceViewVedio.getHolder());
                //异步准备
//                mediaPlayer.prepareAsync();

            }

        } else if (buttonPlayStart.getTag().equals("stop")) {
            if (mediaPlayer != null) {
                mediaPlayer.pause();
                setPlayStartTag();
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer.release();
        super.onDestroy();
    }

    private void play(final int position) {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(vedio_url);
            mediaPlayer.setDisplay(surfaceViewVedio.getHolder());
            mediaPlayer.prepareAsync();

            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Log.d(TAG, "prepared");
                    seekBarVedio.setMax(mediaPlayer.getDuration());
                    mediaPlayer.start();
                    mediaPlayer.seekTo(position);
                    setPlayStopTag();
                    handler.postDelayed(updatePlayProgressRunnable, 100);
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    setPlayStartTag();
                }
            });
            /*mediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                @Override
                public void onSeekComplete(MediaPlayer mp) {
                    Log.d(TAG, "mediaplayer onSeekComplete");
                }
            });*/
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    Log.d(TAG, "onError");
                    mediaPlayer.reset();
                    play(0);
                    return false;
                }
            });
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void stopVedio() {

    }

    private void setPlayStopTag() {
        buttonPlayStart.setText("stop");
        buttonPlayStart.setTag("stop");
    }

    private void setPlayStartTag() {
        buttonPlayStart.setText("start");
        buttonPlayStart.setTag("start");
    }


    private class SurfaceViewCallback implements SurfaceHolder.Callback {
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            Log.d(TAG, "surfaceChanged");
    }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            Log.d(TAG, "surfaceCreated");
            if (position > 0) {
                play(position);
                position = 0;
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            position = mediaPlayer.getCurrentPosition();
            if (mediaPlayer != null
                    && mediaPlayer.isPlaying()) {
                position = mediaPlayer.getCurrentPosition();
                mediaPlayer.stop();
            }
            Log.d(TAG, "surfaceDestroyed position " + position);
        }
    }
}
