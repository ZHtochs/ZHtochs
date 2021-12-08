package com.github.zhtouchs.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.github.zhtouchs.Utils.ZHLog;
import com.github.zhtouchs.ZHActivityManager;

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ZHActivityManager.INSTANCE.addActivity(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ZHLog.d(TAG, "onStart " + this.getClass().getSimpleName());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        ZHLog.d(TAG, "onRestart " + this.getClass().getSimpleName());
    }

    @Override
    protected void onResume() {
        super.onResume();
        ZHLog.d(TAG, "onResume " + this.getClass().getSimpleName());
    }

    @Override
    protected void onPause() {
        super.onPause();
        ZHLog.d(TAG, "onPause " + this.getClass().getSimpleName());

    }

    @Override
    protected void onStop() {
        super.onStop();
        ZHLog.d(TAG, "onStop " + this.getClass().getSimpleName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ZHLog.d(TAG, "onDestroy " + this.getClass().getSimpleName());
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ZHLog.d(TAG, "onConfigurationChanged " + this.getClass().getSimpleName());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZHLog.d(TAG, "onNewIntent " + this.getClass().getSimpleName());
    }

    protected void setLifecycleObserver(ZHLifeObserver lifecycleObserver) {
        getLifecycle().addObserver(lifecycleObserver);
    }
}
