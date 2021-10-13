package com.github.zhtouchs.rxjava.observable;

import com.github.zhtouchs.Utils.ZHLog;
import com.github.zhtouchs.rxjava.disposables.Disposable;
import com.github.zhtouchs.rxjava.disposables.Dispose;
import com.github.zhtouchs.rxjava.functions.Action;
import com.github.zhtouchs.rxjava.observer.Observer;
import com.github.zhtouchs.rxjava.schedulers.Scheduler;

import java.util.function.Consumer;

/**
 * Created by joybar on 2018/6/11.
 */

public abstract class Observable<T> implements ObservableSource<T> {
    private static final String TAG = "Observable";

    public static <T> Observable<T> create(ObservableOnSubscribe<T> source) {
        return new ObservableCreate<T>(source);
    }

    @Override
    public final void subscribe(Observer<? super T> observer) {
        subscribeActual(observer);
    }

    public final Disposable subscribe(Consumer<? super T> onNext, Consumer<? super Throwable> onError,
        Action onComplete, Consumer<? super Disposable> onSubscribe) {

        Dispose<T> disposable = new Dispose<>(onNext, onError, onComplete, onSubscribe);

        subscribe(disposable);

        return disposable;
    }

    public final Disposable subscribe(Consumer<? super T> onNext) {
        return subscribe(onNext, EMPTY_ON_ERROR, EMPTY_ON_COMPLETE, EMPTY_SUBSCRIBE);
    }

    public final Disposable subscribe(Consumer<? super T> onNext, Consumer<? super Throwable> onError) {
        return subscribe(onNext, onError, EMPTY_ON_COMPLETE, EMPTY_SUBSCRIBE);
    }

    protected abstract void subscribeActual(Observer<? super T> observer);

    public final Observable<T> subscribeOn(Scheduler scheduler) {
        return new ObservableSubscribeOn<T>(this, scheduler);
    }

    public final Observable<T> observeOn(Scheduler scheduler) {
        return new ObservableObserveOn<T>(this, scheduler);
    }

    public static final Consumer<Throwable> EMPTY_ON_ERROR = throwable -> ZHLog.d(TAG, "throwable " + throwable);
    public static final Action EMPTY_ON_COMPLETE = () -> ZHLog.d(TAG, "run");
    public static final Consumer<Disposable> EMPTY_SUBSCRIBE = disposable -> ZHLog.d(TAG, "accept " + disposable);

}
