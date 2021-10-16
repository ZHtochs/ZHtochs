package com.github.zhtouchs;

import com.github.zhtouchs.Utils.ZHLog;
import com.github.zhtouchs.Utils.ZHThreadPool;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @program: MyDemo
 * @author: zhuhe
 * @create: 2021-10-15 23:42
 **/
public class ZHAsyncTask<T> {
    private static final String TAG = "AsycTask";

    private Observer<T> observer;

    private final Subscriber<T> subscriber;

    private final AtomicBoolean atomicBoolean;

    private ZHAsyncTask(Subscriber<T> subscriber) {
        this.subscriber = subscriber;
        atomicBoolean = new AtomicBoolean(false);
    }

    public ZHAsyncTask<T> addObserver(Observer<T> observer) {
        ZHLog.i(TAG, "addObserver");
        this.observer = observer;
        subScribeActual();
        return this;
    }

    public static <T> ZHAsyncTask<T> create(Subscriber<T> subscriber) {
        ZHLog.i(TAG, "SubscribeOn");
        return new ZHAsyncTask<T>(subscriber);
    }

    private void subScribeActual() {
        ZHThreadPool.INSTANCE.executeSingle(TAG, () -> {
            try {
                ZHLog.i(TAG, "subScribeActual");
                subscriber.subscribe(ZHAsyncTask.this);
            } catch (Throwable throwable) {
                ZHAsyncTask.this.onError(throwable);
            }
        });
    }

    public void onNext(T t) {
        if (!atomicBoolean.get()) {
            ZHThreadPool.INSTANCE.runOnUi(TAG, new Runnable() {
                @Override
                public void run() {
                    observer.onNext(t);
                }
            });
        }
    }

    public void onError(Throwable e) {
        if (!atomicBoolean.get()) {
            ZHThreadPool.INSTANCE.runOnUi(TAG, new Runnable() {
                @Override
                public void run() {
                    observer.onError(e);
                }
            });
        }
    }

    public interface Subscriber<T> {
        void subscribe(ZHAsyncTask<? super T> tAsycTask) throws Throwable;
    }

    public interface Observer<T> {
        void onError(Throwable throwable);

        void onNext(T t);
    }

    public boolean isDisposed() {
        return atomicBoolean.get();
    }

    public void dispose() {
        atomicBoolean.set(true);
    }
}