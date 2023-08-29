package com.example.drawer.ui.view;

import android.content.Context;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import androidx.viewbinding.ViewBinding;
import com.example.drawer.R;
import com.github.zhtouchs.Utils.ZHLog;

import java.util.List;
import java.util.function.BiConsumer;

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

    private List datas;

    private boolean isFixMode = false;

    private int fixHeight = 0;

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

    public void setFixMode(boolean fixMode) {
        isFixMode = fixMode;
    }

    public <T> void setViewList(List<T> list, @LayoutRes int layoutId, BiConsumer<ViewBinding, T> biConsumer) {
        if (list == null || list.isEmpty() || list.equals(datas)) {
            return;
        }
        this.datas = list;
        needRelayout = true;
        totalSize = list.size();
        removeAllViews();
        final int padding = getContext().getResources().getDimensionPixelOffset(R.dimen.dp_4);
        ViewDataBinding inflate = null;
        for (int i = 0; i < list.size(); i++) {
            inflate = DataBindingUtil.inflate(LayoutInflater.from(getContext()), layoutId, this, false);
            biConsumer.accept(inflate, list.get(i));
            if (i == 0) {
                inflate.getRoot().setPadding(0, 0, 0, padding);
            } else if (i == list.size() - 1) {
                inflate.getRoot().setPadding(0, padding, 0, 0);
            } else {
                inflate.getRoot().setPadding(0, padding, 0, padding);
            }
            if (isFixMode) {
                inflate.getRoot().measure(0, 0);
                fixHeight = inflate.getRoot().getMeasuredHeight();
                ZHLog.d(TAG, "fixHeight " + fixHeight);
            }
            addView(inflate.getRoot());
        }

        expandable = totalSize > MIN_COUNT;


    }

    public void switchState() {
        ZHLog.d(TAG, "switchState isAnimating " + isAnimating);

        if (!expandable || isAnimating) {
            return;
        }
        ExpandAbleAnimation animation;
        int height = isFixMode ? fixHeight : itemHeight;
        ZHLog.d(TAG, "switchState " + collapsed);
        if (collapsed) {
            animation = new ExpandAbleAnimation(MIN_COUNT * height, height * datas.size());
        } else {
            animation = new ExpandAbleAnimation(height * datas.size(), MIN_COUNT * height);
        }
        clearAnimation();
        animation.setAnimationListener(this);
        animation.setDuration(1000);
        startAnimation(animation);
    }

    public void setHeight(int height) {
        Log.e(TAG, "", new Exception());
        getLayoutParams().height = height;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (isFixMode) {
            long start = System.currentTimeMillis();
            extracted1(widthMeasureSpec, heightMeasureSpec);
            ZHLog.d(TAG, "onMeasure2 cost " + (System.currentTimeMillis() - start));
        } else {
            long start = System.currentTimeMillis();
            extracted(widthMeasureSpec, heightMeasureSpec);
            ZHLog.d(TAG, "onMeasure cost " + (System.currentTimeMillis() - start));
        }

    }

    private void extracted1(int widthMeasureSpec, int heightMeasureSpec) {
        if (isAnimating) {
            super.onMeasure(widthMeasureSpec,heightMeasureSpec);
            return;
        }

        if (!expandable) {
            setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), fixHeight * datas.size());
            return;
        }
        if (collapsed) {
            setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), fixHeight * MIN_COUNT);
        } else {
            setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), fixHeight * datas.size());
        }
    }

    private void extracted(int widthMeasureSpec, int heightMeasureSpec) {
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
        ZHLog.d(TAG, "onAnimationStart isAnimating " + true);
        isAnimating = true;
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        isAnimating = false;
        ZHLog.d(TAG, "onAnimationEnd isAnimating " + false);

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