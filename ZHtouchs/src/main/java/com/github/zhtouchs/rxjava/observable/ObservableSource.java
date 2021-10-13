package com.github.zhtouchs.rxjava.observable;

import com.github.zhtouchs.rxjava.observer.Observer;

/**
 * Created by joybar on 2018/6/11.
 */

public interface ObservableSource<T> {
	void subscribe(Observer<? super T> observer);
}