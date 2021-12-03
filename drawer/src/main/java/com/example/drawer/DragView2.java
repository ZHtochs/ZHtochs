package com.example.drawer;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.customview.widget.ViewDragHelper;
import org.jetbrains.annotations.NotNull;

/**
 * @program: MyDemo
 * @author: zhuhe
 * @create: 2021-12-02 22:41
 **/
public class DragView2 extends FrameLayout {
    private static final String TAG = "DragView2";

    private State state = State.IDLE;

    private ViewDragHelper viewDragHelper;

    private float dragStartX;

    private float dragStartY;

    enum State {
        IDLE,
        DRAGGING;
    }

    private int touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

    public DragView2(@NonNull Context context) {
        super(context);
        initViewDragHelper();
    }

    public DragView2(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViewDragHelper();
    }

    public DragView2(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViewDragHelper();
    }

    public DragView2(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initViewDragHelper();
    }

    private void initViewDragHelper() {
        viewDragHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(@NonNull @NotNull View child, int pointerId) {
                return true;
            }

            @Override
            public void onViewCaptured(@NonNull @NotNull View capturedChild, int activePointerId) {
                super.onViewCaptured(capturedChild, activePointerId);
                dragStartX = capturedChild.getLeft();
                dragStartY = capturedChild.getTop();
            }

            @Override
            public void onViewReleased(@NonNull @NotNull View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                viewDragHelper.settleCapturedViewAt((int) dragStartX, (int) dragStartY);
                invalidate();
            }

            @Override
            public int clampViewPositionHorizontal(@NonNull @NotNull View child, int left, int dx) {
                return left;
            }

            @Override
            public int clampViewPositionVertical(@NonNull @NotNull View child, int top, int dy) {
                return top;
            }

            @Override
            public int getViewHorizontalDragRange(View child) {
                return 1;
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                return 1;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (viewDragHelper != null && viewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    private boolean isPointOnView(MotionEvent event) {
        boolean result = false;
        Rect rect = new Rect();
        for (int i = getChildCount() - 1; i >= 0; i--) {
            View view = getChildAt(i);
            rect.set((int) view.getX(), (int) view.getY(), (int) view.getX() + (int) view.getWidth()
                    , (int) view.getY() + view.getHeight());
            if (rect.contains((int) event.getX(), (int) event.getY())) {

                return state != State.DRAGGING;
            }
        }
        return false;
    }
}