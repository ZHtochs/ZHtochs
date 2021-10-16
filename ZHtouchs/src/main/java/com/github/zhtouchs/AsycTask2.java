package com.github.zhtouchs;

import androidx.lifecycle.LiveData;
import com.github.zhtouchs.Utils.ZHLog;
import com.github.zhtouchs.Utils.ZHThreadPool;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @program: MyDemo
 * @author: zhuhe
 * @create: 2021-10-15 23:42
 **/
public class AsycTask2<T> extends LiveData<T> {
    private static final String TAG = "VaLiveData";

    private Observer<T> observer;

    private final AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    public AsycTask2() {
        atomicBoolean.set(false);
    }

    public AsycTask2<T> addObserver(Observer<T> observer) {
        ZHLog.i(TAG, "addObserver");
        this.observer = observer;
        return this;
    }

    public AsycTask2<T> SubscribeOn(Subscriber<T> subscriber) {
        ZHThreadPool.INSTANCE.executeSingle(TAG, () -> {
            try {
                subscriber.Subscribe(AsycTask2.this);
            } catch (Throwable throwable) {
                AsycTask2.this.onError(throwable);
            }
        });
        return this;
    }

    public void onNext(T t) {
        if (!atomicBoolean.get()) {
            postValue(t);
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
        void Subscribe(AsycTask2<? super T> tAsycTask) throws Throwable;
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