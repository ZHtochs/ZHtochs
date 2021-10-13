/**
 * Copyright (c) 2016-present, RxJava Contributors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See
 * the License for the specific language governing permissions and limitations under the License.
 */

package com.github.zhtouchs.rxjava.disposables;

import com.github.zhtouchs.Utils.ZHLog;
import com.github.zhtouchs.rxjava.functions.Action;
import com.github.zhtouchs.rxjava.observer.Observer;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public final class Dispose<T> extends AtomicReference<Boolean>
        implements Observer<T>, Disposable {

    private final Consumer<? super T> onNext;
    private final Consumer<? super Throwable> onError;
    private final Action onComplete;
    private final Consumer<? super Disposable> onSubscribe;

    public Dispose(Consumer<? super T> onNext, Consumer<? super Throwable> onError,
                   Action onComplete, Consumer<? super Disposable> onSubscribe) {
        this.onNext = onNext;
        this.onError = onError;
        this.onComplete = onComplete;
        this.onSubscribe = onSubscribe;
        set(false);
    }

    @Override
    public void onSubscribe() {
        if (isDisposed()) {
            try {
                onSubscribe.accept(this);
            } catch (Throwable e) {
                onError.accept(e);
            }
        }
    }

    @Override
    public void onNext(T t) {
        if (!isDisposed()) {
            try {
                onNext.accept(t);
            } catch (Throwable e) {
                onError.accept(e);
            }
        }
    }

    @Override
    public void onError(Throwable t) {
        if (!isDisposed()) {
            onError.accept(t);
        }
    }

    @Override
    public void onComplete() {
        if (!isDisposed()) {
            onComplete.run();
        }
    }

    @Override
    public void dispose() {
        ZHLog.i("Dispose", this.hashCode() + " dispose true");
        set(true);
    }

    @Override
    public boolean isDisposed() {
        ZHLog.i("Dispose", this.hashCode() + " isDisposed " + get());
        return get();
    }
}
