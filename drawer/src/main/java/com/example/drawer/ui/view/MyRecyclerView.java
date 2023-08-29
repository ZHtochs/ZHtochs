package com.example.drawer.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.github.zhtouchs.Utils.ZHLog;
import org.jetbrains.annotations.NotNull;

/**
 * @program: MyDemo
 * @author: zhuhe
 * @create: 2023-08-30 01:01
 **/
public class MyRecyclerView extends RecyclerView {
    private static final String TAG = "MyRecyclerView";

    public boolean isAnimating = false;

    public MyRecyclerView(@NonNull @NotNull Context context) {
        super(context);
    }

    public MyRecyclerView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecyclerView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        long start = System.currentTimeMillis();
        if (isAnimating) {
            setMeasuredDimension(MeasureSpec.getSize(widthSpec), MeasureSpec.getSize(heightSpec));
        } else {
            super.onMeasure(widthSpec, heightSpec);
        }
        ZHLog.d(TAG, "onMeasure cost " + (System.currentTimeMillis() - start));
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(false);
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        return false;
    }
}