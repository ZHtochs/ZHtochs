package com.example.app;

import android.app.Application;
import android.content.res.Configuration;

import androidx.annotation.NonNull;

import com.github.zhtouchs.ZHActivityManager;

public class ExApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ZHActivityManager.INSTANCE.setContext(this);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ZHActivityManager.INSTANCE.setContext(this);
    }
}
