package com.github.zhtouchs.rxjava.utils;

import androidx.annotation.Nullable;

/**
 * Created by joybar on 2018/5/14.
 */

public class CheckUtils {
    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        } else {
            return reference;
        }
    }

    public static <T> T checkNotNull(T reference, @Nullable Object errorMessage) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        } else {
            return reference;
        }
    }

    public static void checkError(boolean isError, @Nullable Object errorMessage) {
        if (isError) {
            throw new NullPointerException(String.valueOf(errorMessage));
        }
    }

}
