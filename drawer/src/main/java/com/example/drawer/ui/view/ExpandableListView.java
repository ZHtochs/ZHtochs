package com.example.drawer.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import com.github.zhtouchs.Utils.ZHLog;
import org.jetbrains.annotations.NotNull;

/**
 * @program: MyDemo
 * @author: zhuhe
 * @create: 2022-02-08 23:25
 **/
public class ExpandableListView extends ListView implements Animation.AnimationListener {
    private static final String TAG = "ExpandableRecyclerView";

    public static final int MIN_COUNT = 5;

    private boolean isAnimating = false;

    private boolean expandable = false;

    private boolean collapsed = true;

    private final SparseIntArray array = new SparseIntArray();

    private ExpandableLayout.OnExpandListener expandListener;

    private int totalSize;

    private int totalHeight;

    private boolean needRelayout = true;

    public ExpandableListView(@NonNull @NotNull Context context) {
        this(context, null);
    }

    public ExpandableListView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandableListView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return false;
    }

    public void switchState() {
        if (!expandable || isAnimating) {
            return;
        }
        ExpandAbleAnimation animation;
        ZHLog.d(TAG, "switchState " + collapsed);
        if (collapsed) {
            animation = new ExpandAbleAnimation(getMinHeight(), totalHeight);
        } else {
            animation = new ExpandAbleAnimation(totalHeight, getMinHeight());
        }
        animation.setAnimationListener(this);
        startAnimation(animation);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        totalSize = getChildCount();
        ZHLog.d(TAG, "onMeasure " + totalSize);
        if (totalSize == 0) {
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
        for (int i = 0; i < totalSize; i++) {
            array.append(i, getChildAt(i).getMeasuredHeight());
        }
        expandable = totalSize > MIN_COUNT;
        ZHLog.d(TAG, "totalHeight " + totalHeight);
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (expandable) {
            layoutParams.height = getMinHeight();
        }
    }

    private int getMinHeight() {
        int sum = 0;
        for (int i = 0; i < MIN_COUNT; i++) {
            sum += array.get(i);
        }
        ZHLog.d(TAG, "getMinHeight " + totalHeight);
        return sum;
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
        void onStateChanged(View view, boolean isExpand);
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
            ExpandableListView.this.getLayoutParams().height = startHeight;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation transformation) {
            int height = startHeight + (int) ((endHeight - startHeight) * interpolatedTime);
            ExpandableListView.this.getLayoutParams().height = height;
            ExpandableListView.this.requestLayout();
        }
    }

}