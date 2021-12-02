package com.example.drawer;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import com.github.zhtouchs.Utils.ZHLog;

/**
 * @program: MyDemo
 * @author: zhuhe
 * @create: 2021-12-02 22:41
 **/
public class DragView extends FrameLayout {
    private static final String TAG = "DragView";

    private float lastPointX;

    private float lastPointY;

    private float dragStartX;

    private float dragStartY;

    private View dragingView;

    private State state;

    enum State {
        IDLE,
        DRAGGING;
    }

    private int touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

    public DragView(@NonNull Context context) {
        super(context);
    }

    public DragView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DragView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DragView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (isPointOnView(event)) {
                    state = State.DRAGGING;
                    lastPointX = event.getX();
                    lastPointY = event.getY();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaX = event.getX() - lastPointX;
                float deltaY = event.getY() - lastPointY;
                if (state == State.DRAGGING && dragingView != null
                        && ((deltaX * deltaX + deltaY * deltaY) > (touchSlop * touchSlop / 10.0))) {
                    //正在移动
                    ViewCompat.offsetLeftAndRight(dragingView, (int) deltaX);
                    ViewCompat.offsetTopAndBottom(dragingView, (int) deltaY);

                    lastPointX = event.getX();
                    lastPointY = event.getY();
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (state == State.DRAGGING) {
                    if (dragingView != null) {
                        ValueAnimator valueAnimatorX = ValueAnimator.ofFloat(dragingView.getX(), dragStartX);
                        valueAnimatorX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                dragingView.setX((Float) animation.getAnimatedValue());
                            }
                        });
                        ValueAnimator valueAnimatorY = ValueAnimator.ofFloat(dragingView.getY(), dragStartY);
                        valueAnimatorY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                dragingView.setY((Float) animation.getAnimatedValue());
                            }
                        });
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.play(valueAnimatorX).with(valueAnimatorY);
                        animatorSet.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation, boolean isReverse) {
                                super.onAnimationEnd(animation, isReverse);
                                dragingView = null;
                                state = State.IDLE;
                            }
                        });
                        animatorSet.start();
                    }

                }
        }
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    private boolean isPointOnView(MotionEvent event) {
        boolean result = false;
        Rect rect = new Rect();
        for (int i = getChildCount() - 1; i >= 0; i--) {
            View view = getChildAt(i);
            rect.set((int) view.getX(), (int) view.getY(), (int) view.getX() + (int) view.getWidth()
                    , (int) view.getY() + view.getHeight());
            if (rect.contains((int) event.getX(), (int) event.getY())) {
                dragingView = view;

                dragStartX = dragingView.getX();
                dragStartY = dragingView.getY();
                ZHLog.d(TAG, "dragStartX " + dragStartX + " dragStartY " + dragStartY);
                return state != State.DRAGGING;
            }
        }
        return false;
    }
}