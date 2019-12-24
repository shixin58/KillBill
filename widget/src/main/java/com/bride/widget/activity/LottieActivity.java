package com.bride.widget.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.bride.ui_lib.BaseActivity;
import com.bride.widget.R;

/**
 * <p>Created by shixin on 2019-12-24.
 */
public class LottieActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottie);
    }

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, LottieActivity.class);
        context.startActivity(intent);
    }

    public void onClick(View v) {
        LottieAnimationView animationView = findViewById(R.id.lottie_animation_view);
        switch (v.getId()) {
            case R.id.tv_android_wave:
                animationView.setImageAssetsFolder("images/");
                animationView.setRepeatCount(LottieDrawable.INFINITE);
                animationView.setAnimation("AndroidWave.json");
                animationView.playAnimation();
                break;
            case R.id.tv_heart:
                animationView.setRepeatCount(0);
                animationView.setAnimation(R.raw.heart);
                animationView.playAnimation();
                break;
            case R.id.tv_loading:
                animationView.setRepeatCount(LottieDrawable.INFINITE);
                animationView.setAnimation(R.raw.loading);
                animationView.playAnimation();
                break;
            case R.id.tv_roulette:
                animationView.setRepeatCount(LottieDrawable.INFINITE);
                animationView.setAnimation(R.raw.roulette);
                animationView.playAnimation();
                break;
            case R.id.tv_gold:
                animationView.setRepeatCount(LottieDrawable.INFINITE);
                animationView.setAnimation(R.raw.gold);
                animationView.playAnimation();
                break;
            case R.id.tv_firework:
                animationView.setRepeatCount(LottieDrawable.INFINITE);
                animationView.setAnimation(R.raw.firework);
                animationView.playAnimation();
                break;
        }
    }
}
