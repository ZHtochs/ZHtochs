package com.github.zhtouchs;

import com.github.zhtouchs.Utils.ZHThreadPool;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @program: MyDemo
 * @author: zhuhe
 * @create: 2021-10-15 23:42
 **/
public class AsycTask<T> extends AtomicBoolean {
    private static final String TAG = "VaLiveData";

    private Observer<T> observer;

    public AsycTask() {
        super(false);
    }

    public AsycTask<T> addObserver(Observer<T> observer) {
        this.observer = observer;
        return this;
    }

    public AsycTask<T> SubscribeOn(Subscriber<T> subscriber) {
        subScribeActual(subscriber);
        return this;
    }

    public void subScribeActual(Subscriber<T> subscriber) {
        ZHThreadPool.INSTANCE.executeSingle(TAG, () -> {
            try {
                subscriber.Subscribe(AsycTask.this);
            } catch (Throwable throwable) {
                AsycTask.this.onError(throwable);
            }
        });
    }

    public void onNext(T t) {
        if (!get()) {
            ZHThreadPool.INSTANCE.runOnUi(TAG, new Runnable() {
                @Override
                public void run() {
                    observer.onNext(t);
                }
            });
        }
    }

    public void onError(Throwable e) {
        if (!get()) {
            ZHThreadPool.INSTANCE.runOnUi(TAG, new Runnable() {
                @Override
                public void run() {
                    observer.onError(e);
                }
            });
        }
    }

    public interface Subscriber<T> {
        public void Subscribe(AsycTask<? super T> tAsycTask) throws Throwable;
    }

    public interface Observer<T> {
        public void onError(Throwable throwable);

        public void onNext(T t);
    }

    public boolean isDisposed() {
        return get();
    }

    public void dispose() {
        set(true);
    }
}