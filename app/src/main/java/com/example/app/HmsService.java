package com.example.app;

import android.os.Bundle;

import com.github.zhtouchs.Utils.ZHLog;
import com.huawei.hms.push.HmsMessageService;

public class HmsService extends HmsMessageService {
    private static final String TAG = "HmsService";

    @Override
    public void onNewToken(String s) {
        ZHLog.d(TAG, "onNewToken " + s);
        super.onNewToken(s);
    }

    @Override
    public void onNewToken(String s, Bundle bundle) {
        ZHLog.d(TAG, "onNewToken with bundle " + s);

        super.onNewToken(s, bundle);
    }
}
