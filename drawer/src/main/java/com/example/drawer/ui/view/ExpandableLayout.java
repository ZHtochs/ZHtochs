package com.example.drawer.ui.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import com.github.zhtouchs.Utils.ZHLog;

import java.util.List;

/**
 * @program: MyDemo
 * @author: zhuhe
 * @create: 2022-01-21 23:26
 **/
public class ExpandableLayout extends LinearLayout implements Animation.AnimationListener {
    private static final String TAG = "ExpandableLayout";

    public static final int MIN_COUNT = 5;

    private boolean isAnimating = false;

    private boolean expandable = false;

    private boolean collapsed = false;

    private OnExpandListener expandListener;

    private int totalSize;

    private int itemHeight;

    private int totalHeight;

    private boolean needRelayout;

    public ExpandableLayout(Context context) {
        this(context, null);
    }

    public ExpandableLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandableLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ExpandableLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        super.setOrientation(VERTICAL);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isAnimating;
    }

    @Override
    public void setOrientation(int orientation) {
        if (orientation != VERTICAL) {
            throw new IllegalStateException("orientation must be VERTICAL");
        }
        super.setOrientation(orientation);
    }

    public void setViewList(@Nullable List<View> viewList) {
        if (viewList == null || viewList.isEmpty()) {
            setVisibility(GONE);
            return;
        }
        needRelayout = true;
        totalSize = viewList.size();
        for (View view : viewList) {
            addView(view);
        }
    }

    public void switchState() {
        if (!expandable || isAnimating) {
            return;
        }
        ExpandAbleAnimation animation;
        ZHLog.d(TAG, "switchState " + collapsed);
        ObjectAnimator animator;
        if (collapsed) {
            animator = ObjectAnimator.ofInt(this, "height", MIN_COUNT * itemHeight, totalHeight);
        } else {
            animator = ObjectAnimator.ofInt(this, "height", totalHeight, MIN_COUNT * itemHeight);
        }
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                collapsed = !collapsed;
            }
        });
        animator.setDuration(1000);
        animator.start();
    }

    public void setHeight(int height) {
        Log.e(TAG, "", new Exception());
        getLayoutParams().height = height;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (totalSize == 0) {
            setVisibility(GONE);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        if (!needRelayout) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        needRelayout = false;

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        totalHeight = getMeasuredHeight();
        itemHeight = totalHeight / totalSize;
        expandable = totalSize > MIN_COUNT;
        ZHLog.d(TAG, "itemHeight " + itemHeight);
        ZHLog.d(TAG, "totalHeight " + totalHeight);

        int width = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        int actualHeight;
        if (expandable) {
            actualHeight = MeasureSpec.makeMeasureSpec(itemHeight * MIN_COUNT, MeasureSpec.EXACTLY);
        } else {
            actualHeight = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        }
        super.onMeasure(width, actualHeight);
    }

    @Override
    public void onAnimationStart(Animation animation) {
        isAnimating = true;
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        isAnimating = false;
        collapsed = !collapsed;
        ZHLog.d(TAG, "collapsed " + collapsed);
        if (expandListener != null) {
            expandListener.onStateChanged(this, !collapsed);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    public interface OnExpandListener {
        public void onStateChanged(View view, boolean isExpand);
    }

    private class ExpandAbleAnimation extends Animation {

        final int startHeight;
        final int endHeight;

        public ExpandAbleAnimation(int startHeight, int endHeight) {
            this.startHeight = startHeight;
            this.endHeight = endHeight;
            ZHLog.d(TAG, "startHeight " + startHeight + " endHeight " + endHeight);
            setDuration(300);
            setInterpolator(new FastOutLinearInInterpolator());
            ExpandableLayout.this.getLayoutParams().height = startHeight;

        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation transformation) {
            int height = startHeight + (int) ((endHeight - startHeight) * interpolatedTime);
            ExpandableLayout.this.getLayoutParams().height = height;
            ExpandableLayout.this.requestLayout();
        }
    }
}