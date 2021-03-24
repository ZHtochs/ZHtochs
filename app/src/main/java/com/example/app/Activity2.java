package com.example.app;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.github.zhtouchs.Utils.ZHLog;

public class Activity2 extends Activity {
    private static final String TAG = "Activity2";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ZHLog.d(TAG, "onCreate");
        setContentView(R.layout.activity2);
    }
}
