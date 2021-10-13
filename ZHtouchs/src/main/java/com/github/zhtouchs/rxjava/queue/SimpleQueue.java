package com.github.zhtouchs.rxjava.queue;

import androidx.annotation.Nullable;

/**
 * Created by joybar on 16/06/2018.
 */

public interface SimpleQueue<T>  {
    @Nullable
    T poll() throws Exception;
}