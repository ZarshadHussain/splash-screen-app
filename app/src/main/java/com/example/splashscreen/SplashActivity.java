package com.example.splashscreen;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_DURATION = 2600L;

    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        View logo = findViewById(R.id.logoContainer);
        View title = findViewById(R.id.appName);
        View tagline = findViewById(R.id.tagline);
        View track = findViewById(R.id.progressTrack);
        View fill = findViewById(R.id.progressFill);
        View version = findViewById(R.id.versionText);

        logo.setAlpha(0f);
        logo.setScaleX(0.82f);
        logo.setScaleY(0.82f);
        View[] others = {title, tagline, track, version};
        for (View v : others) {
            v.setAlpha(0f);
            v.setTranslationY(40f);
        }

        logo.animate()
                .alpha(1f).scaleX(1f).scaleY(1f)
                .setDuration(700)
                .setInterpolator(new OvershootInterpolator(1.2f))
                .start();

        slideUp(title, 250);
        slideUp(tagline, 380);
        slideUp(track, 500);
        slideUp(version, 500);

        fill.setPivotX(0f);
        fill.setScaleX(0f);
        ObjectAnimator barAnim = ObjectAnimator.ofFloat(fill, View.SCALE_X, 0f, 1f);
        barAnim.setStartDelay(500);
        barAnim.setDuration(SPLASH_DURATION - 600);
        barAnim.setInterpolator(new DecelerateInterpolator());
        barAnim.start();

        handler.postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }, SPLASH_DURATION);
    }

    private void slideUp(View view, long delay) {
        view.animate()
                .alpha(1f)
                .translationY(0f)
                .setStartDelay(delay)
                .setDuration(700)
                .setInterpolator(new DecelerateInterpolator())
                .start();
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}