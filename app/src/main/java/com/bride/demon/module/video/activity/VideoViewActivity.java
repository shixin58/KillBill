package com.bride.demon.module.video.activity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bride.demon.R;
import com.bride.demon.databinding.ActivityVideoViewBinding;
import com.bride.ui_lib.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 展示用系统VideoView播放资源文件(res/raw/*.mp4)
 * <p>Created by shixin on 2019-08-20.
 */
public class VideoViewActivity extends BaseActivity {
    private ActivityVideoViewBinding mBinding;

    private SeekBar mSeekBar;
    private ToggleButton mToggleButton;
    private ProgressBar mLoading;
    private TextView mTvConsumed;
    private TextView mTvRemained;
    private TextView mTvCountdown;

    private final Timer mTimer = new Timer();
    private TimerTask mTimerTask;

    private CustomCountDownTimer mCountDownTimer;

    private boolean mPlayingBeforeDragging = false;

    private class ProgressTimerTask extends TimerTask {
        @Override
        public void run() {
            mSeekBar.post(new Runnable() {
                @Override
                public void run() {
                    mSeekBar.setProgress(mBinding.videoView.getCurrentPosition());
                }
            });
        }
    }

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, VideoViewActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityVideoViewBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.setActivity(this);

        initView();
    }

    private void initView() {
        mSeekBar = findViewById(R.id.seekBar);
        mToggleButton = findViewById(R.id.btn_start);
        mLoading = findViewById(R.id.loading);
        mTvConsumed = findViewById(R.id.tv_consumed);
        mTvRemained = findViewById(R.id.tv_remained);
        mTvCountdown = findViewById(R.id.tv_countdown);

        mBinding.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mSeekBar.setMax(mp.getDuration());
                mp.start();
                mTimer.schedule(mTimerTask = new ProgressTimerTask(), 1000L, 1000L);
                mToggleButton.setChecked(true);

                mp.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                    @Override
                    public void onSeekComplete(MediaPlayer mp) {
                        if (mPlayingBeforeDragging) {
                            mPlayingBeforeDragging = false;
                            mBinding.videoView.start();
                        }
                    }
                });
            }
        });
        mBinding.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mSeekBar.setProgress(mBinding.videoView.getCurrentPosition());
                mTimerTask.cancel();
                mToggleButton.setChecked(false);
            }
        });
        mBinding.videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                return false;
            }
        });
        mBinding.videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                switch (what) {
                    case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                        mLoading.setVisibility(View.VISIBLE);
                        break;
                    case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                        mLoading.setVisibility(View.GONE);
                        break;
                }
                return false;
            }
        });
        // 网络源用mBinding.videoView.setVideoURI(Uri.parse(""));
        mBinding.videoView.setVideoPath("android.resource://" + getPackageName() + "/raw/egg_shell");

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mTvConsumed.setText((progress/60_000)+":"+(progress%60_000/1000));
                int remained = seekBar.getMax() - progress;
                mTvRemained.setText((remained/60_000)+":"+(remained%60_000/1000));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mPlayingBeforeDragging = mBinding.videoView.isPlaying();
                mBinding.videoView.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mBinding.videoView.seekTo(seekBar.getProgress());
            }
        });

        // 使用MediaController控制视频播放
        MediaController mediaController = new MediaController(this, true);
        mBinding.videoView.setMediaController(mediaController);
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id==R.id.btn_start) {
            if (mToggleButton.isChecked()) {
                mBinding.videoView.start();
                mTimer.schedule(mTimerTask = new ProgressTimerTask(), 1000L, 1000L);
            } else {
                mBinding.videoView.pause();
                mTimerTask.cancel();
            }
        } else if (id==R.id.tv_countdown) {
            if (mCountDownTimer == null) {
                mCountDownTimer = new CustomCountDownTimer(60_000, 1000);
                mCountDownTimer.start();
            } else {
                mCountDownTimer.cancel();
                mCountDownTimer = null;
                mTvCountdown.setText(String.valueOf(60));
            }
        }
    }

    private class CustomCountDownTimer extends CountDownTimer {

        CustomCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            mTvCountdown.setText(String.valueOf(millisUntilFinished/1000));
        }

        @Override
        public void onFinish() {
            mCountDownTimer = null;
            mTvCountdown.setText(String.valueOf(60));
        }
    }

    @Override
    protected void onDestroy() {
        mTimer.cancel();
        super.onDestroy();
    }
}
