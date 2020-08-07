package com.belajar.submissionuiux.Ui.Activity;

import android.content.pm.PackageInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.belajar.submissionuiux.Network.Const;
import com.belajar.submissionuiux.R;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashActivity extends AppCompatActivity {

    String ver = "1.0";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            ver = packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }

        EasySplashScreen config = new EasySplashScreen(SplashActivity.this)
                .withFullScreen()
                .withTargetActivity(UserSearchActivity.class)
                .withBackgroundColor(getResources().getColor(R.color.white))
                .withFooterText("App Version" + ver)
                .withLogo(R.drawable.welcome);

        config.getFooterTextView().getResources().getColor(R.color.bg);

        View easySplashScreen = config.create();
        setContentView(easySplashScreen);
    }
}
