package com.belajar.submissionuiux.Ui.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.belajar.submissionuiux.R;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        EasySplashScreen config = new EasySplashScreen(SplashActivity.this)
                .withFullScreen()
                .withTargetActivity(UserSearchActivity.class)
                .withBackgroundColor(getResources().getColor(R.color.white))
                .withLogo(R.drawable.welcome);

        View easySplashScreen = config.create();
        setContentView(easySplashScreen);
    }
}
