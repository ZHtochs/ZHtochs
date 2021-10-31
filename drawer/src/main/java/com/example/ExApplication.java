package com.example;

import android.app.Application;
import android.content.res.Configuration;
import androidx.annotation.NonNull;
import com.github.zhtouchs.Utils.ZHLog;
import com.github.zhtouchs.ZHActivityManager;

public class ExApplication extends Application {
    private static final String TAG = "ExApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        ZHActivityManager.INSTANCE.setContext(this);
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
                ZHLog.e(TAG, e.getMessage());
                ZHLog.e(TAG, e.getClass());
                for (StackTraceElement stackTraceElement : e.getStackTrace()) {
                    ZHLog.e(TAG, stackTraceElement);
                }
            }
        });
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ZHActivityManager.INSTANCE.setContext(this);
    }
}
