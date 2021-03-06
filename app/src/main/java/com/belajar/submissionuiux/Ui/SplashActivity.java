package com.belajar.submissionuiux.Ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private Handler mHandler;
    private Runnable mRunnable;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRunnable = () -> {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        };

        mHandler = new Handler();
        mHandler.postDelayed(mRunnable,2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null && mRunnable != null) {
            mHandler.removeCallbacks(mRunnable);
        }
    }
}
