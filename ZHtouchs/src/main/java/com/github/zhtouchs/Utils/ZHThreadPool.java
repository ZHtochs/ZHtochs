package com.github.zhtouchs.Utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public enum ZHThreadPool {
    INSTANCE;
    private static final String TAG = "ZHThreadPool";

    private static final Handler handler = new Handler(Looper.getMainLooper());

    private static final BlockingDeque<Runnable> blockingDeque = new LinkedBlockingDeque<>(20);
    private static final ExecutorService threadPool = new ThreadPoolExecutor(
            Runtime.getRuntime().availableProcessors(), 20, 5000, TimeUnit.MILLISECONDS,
            blockingDeque);
    private static final ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();

    public void execute(String tag, Runnable runnable) {
        ZHLog.d(TAG, "execute:" + tag);
        threadPool.execute(runnable);
    }

    public void executeSingle(String tag, Runnable runnable) {
        ZHLog.d(TAG, "executeSingle:" + tag);
        singleThreadPool.execute(runnable);
    }

    public Future execute(String tag, Callable callable) {
        ZHLog.d(TAG, "execute Future:" + tag);
        return threadPool.submit(callable);
    }

    public Future executeSingle(String tag, Callable callable) {
        ZHLog.d(TAG, "executeSingle Future:" + tag);
        return singleThreadPool.submit(callable);
    }

    public void runOnUi(String tag, Runnable runnable) {
        ZHLog.d(TAG, "runOnUi:" + tag);
        handler.post(runnable);
    }

    public void runOnUi(String tag, long delay, Runnable runnable) {
        ZHLog.d(TAG, "runOnUi:" + tag);
        handler.postDelayed(runnable, delay);
    }
}
