package com.github.zhtouchs.hook;

import android.os.Build;
import com.github.zhtouchs.Utils.ZHLog;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

public class HookHelper {
    public static final String TARGET_INTENT = "target_intent";

    public static void hookAMS() {
        try {
            Field defaultFiled = null;
            Object iActivityManagerObject = null;
            Field mInstance = null;
            if (Build.VERSION.SDK_INT > 28) {
                Class<?> clazz = Class.forName("android.app.ActivityTaskManager");
                defaultFiled = clazz.getDeclaredField("IActivityTaskManagerSingleton");
                defaultFiled.setAccessible(true);
                Object defaultValue = defaultFiled.get(null);

                if (defaultValue == null) {
                    ZHLog.i("testabc", "efaultValue==null");
                }
                //反射SingleTon
                Class<?> SingletonClass = Class.forName("android.util.Singleton");
                mInstance = SingletonClass.getDeclaredField("mInstance");
                mInstance.setAccessible(true);
                iActivityManagerObject = mInstance.get(defaultValue);
                if (iActivityManagerObject != null) {
                    ZHLog.e("testabc", "IActivityTaskManagerSingleton 2 ");
                    //开始动态代理，用代理对象替换掉真实的ActivityManager，瞒天过海
                    Class<?> IActivityManagerIntercept = Class.forName("android.app.IActivityTaskManager");
                    IActivityManagerProxy handler = new IActivityManagerProxy(iActivityManagerObject);
                    Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[]{IActivityManagerIntercept}, handler);
                    //现在替换掉这个对象
                    mInstance.set(defaultValue, proxy);
                    ZHLog.e("testabc", "IActivityTaskManagerSingleton 3 ");
                } else {
                    ZHLog.e("testabc", "IActivityTaskManagerSingleton: is null ");
                }

            } else if (Build.VERSION.SDK_INT > 25 || (Build.VERSION.SDK_INT == 25 && Build.VERSION.PREVIEW_SDK_INT > 0)) {
                Class<?> clazz = Class.forName("android.app.ActivityManager");
                defaultFiled = clazz.getDeclaredField("IActivityManagerSingleton");
                defaultFiled.setAccessible(true);
                Object defaultValue = defaultFiled.get(null);
                //反射SingleTon
                Class<?> SingletonClass = Class.forName("android.util.Singleton");
                mInstance = SingletonClass.getDeclaredField("mInstance");
                mInstance.setAccessible(true);
                iActivityManagerObject = mInstance.get(defaultValue);
                //开始动态代理，用代理对象替换掉真实的ActivityManager，瞒天过海
                Class<?> IActivityManagerIntercept = Class.forName("android.app.IActivityManager");
                IActivityManagerProxy handler = new IActivityManagerProxy(iActivityManagerObject);
                Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[]{IActivityManagerIntercept}, handler);
                //现在替换掉这个对象
                mInstance.set(defaultValue, proxy);
            } else {
                Class<?> ActivityManagerNativeClss = Class.forName("android.app.ActivityManagerNative");
                defaultFiled = ActivityManagerNativeClss.getDeclaredField("gDefault");
                ZHLog.e("HookUtil", "singleton：123");
                defaultFiled.setAccessible(true);
                Object defaultValue = defaultFiled.get(null);
                //反射SingleTon
                Class<?> SingletonClass = Class.forName("android.util.Singleton");
                ZHLog.e("HookUtil", "singleton：456");
                mInstance = SingletonClass.getDeclaredField("mInstance");
                mInstance.setAccessible(true);
                //到这里已经拿到ActivityManager对象
                iActivityManagerObject = mInstance.get(defaultValue);
                //开始动态代理，用代理对象替换掉真实的ActivityManager，瞒天过海
                Class<?> IActivityManagerIntercept = Class.forName("android.app.IActivityManager");

                IActivityManagerProxy handler = new IActivityManagerProxy(iActivityManagerObject);
                Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                        new Class<?>[]{IActivityManagerIntercept}, handler);

                //现在替换掉这个对象
                mInstance.set(defaultValue, proxy);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
