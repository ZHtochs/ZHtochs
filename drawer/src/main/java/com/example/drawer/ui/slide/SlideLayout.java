package com.example.drawer.ui.slide;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.customview.widget.ViewDragHelper;
import androidx.slidingpanelayout.widget.SlidingPaneLayout;
import com.github.zhtouchs.Utils.ZHLog;
import org.jetbrains.annotations.NotNull;

/**
 * @program: MyDemo
 * @author: zhuhe
 * @create: 2021-11-17 22:14
 **/
public class SlideLayout extends SlidingPaneLayout {
    private static final String TAG = "SlideLayout";

    private View contentView;

    private View sideView;

    private int startX;
    private int startY;

    private ViewDragHelper viewDragHelper;

    boolean isTouchDown;

    private SlideListener slideListener;

    public SlideLayout(@NonNull @NotNull Context context) {
        super(context);
    }

    public SlideLayout(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SlideLayout(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) event.getX();
                startY = (int) event.getY();
                isTouchDown = false;
                break;
            case MotionEvent.ACTION_MOVE:
                final float x = event.getX();
                final float y = event.getY();
                int TouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
                int xDiff = (int) Math.abs(x - startX);
                int yDiff = (int) Math.abs(y - startY);
                //勾股定理
                final int x_yDiff = xDiff * xDiff + yDiff * yDiff;
                //判断是否满足移动条件
                boolean xMoved = x_yDiff > TouchSlop * TouchSlop;
                ZHLog.d(TAG, "xMoved" + xMoved);
                if (xMoved) {
                    //横轴方向大于纵轴方向的四倍的话拦截事件自己(抽屉效果)处理,否则不拦截交给子布局listview滑动处理
                    getParent().requestDisallowInterceptTouchEvent(true);
                    if (!isTouchDown) {
                        slideListener.isGoingToSlide(this);
                        isTouchDown = true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void setPanelSlideListener(@Nullable PanelSlideListener listener) {
        super.setPanelSlideListener(listener);
    }

    public void setListener(SlideListener slideListener) {
        setPanelSlideListener(slideListener);
        this.slideListener = slideListener;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        sideView = getChildAt(0);
        contentView = getChildAt(1);
        viewDragHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(@NonNull @NotNull View child, int pointerId) {
                return true;
            }
        });
        viewDragHelper.captureChildView(contentView, 0);
    }

    public interface SlideListener extends PanelSlideListener {

        void isGoingToSlide(SlideLayout slideLayout);

        @Override
        void onPanelSlide(@NonNull @NotNull View panel, float slideOffset);

        @Override
        void onPanelOpened(@NonNull @NotNull View panel);

        @Override
        void onPanelClosed(@NonNull @NotNull View panel);
    }
}