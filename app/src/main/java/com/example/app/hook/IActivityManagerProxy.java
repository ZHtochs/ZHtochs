package com.example.app.hook;

import android.content.Intent;

import com.example.app.ProxyActivity;
import com.github.zhtouchs.Utils.ZHLog;
import com.github.zhtouchs.ZHActivityManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class IActivityManagerProxy implements InvocationHandler {
    private static final String TAG = "IActivityManagerProxy";
    private final Object mActivityManager;

    public IActivityManagerProxy(Object mActivityManager) {
        this.mActivityManager = mActivityManager;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        ZHLog.d(TAG, method.getName());
        if ("startActivity".equals(method.getName())) {
            Intent intent = null;
            int index = 0;
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof Intent) {
                    index = i;
                    intent = (Intent) args[i];
                    break;
                }
            }
            Intent subIntent = new Intent();
            String packageName = ZHActivityManager.INSTANCE.getContext().getPackageName();
            subIntent.setClassName(packageName, ProxyActivity.class.getName());
            subIntent.putExtra("target_intent", intent);
            args[index] = subIntent;
        }
        return method.invoke(mActivityManager, args);
    }
}
