package com.github.zhtouchs.activity;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.github.zhtouchs.Utils.ZHLog;

public class ZHLifeObserver implements LifecycleObserver {

    private static final String TAG = "MyLifeObserver";

    private String name;

    public ZHLifeObserver(String name) {
        this.name = name;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onActivityCreate() {
        ZHLog.d(TAG, name+"onActivityCreate");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onActivityResume() {
        ZHLog.d(TAG, name+"onActivityResume");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onActivityPause() {
        ZHLog.d(TAG, name+"onActivityPause");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onActivityStop() {
        ZHLog.d(TAG, name+"onActivityStop");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onActivityDestroy() {
        ZHLog.d(TAG, name+"onActivityDestroy");
    }
}
