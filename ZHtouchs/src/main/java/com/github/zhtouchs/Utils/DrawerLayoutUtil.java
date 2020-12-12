package com.github.zhtouchs.Utils;

import android.app.Activity;
import android.graphics.Point;
import android.view.Gravity;

import androidx.annotation.NonNull;
import androidx.customview.widget.ViewDragHelper;
import androidx.drawerlayout.widget.DrawerLayout;

import java.lang.reflect.Field;

public class DrawerLayoutUtil {
    private static final String TAG = "DrawerLayoutUtil";

    private DrawerLayoutUtil() {
    }

    public static void setDrawerEdgeSize(@NonNull Activity activity, @NonNull DrawerLayout drawerLayout, double displayWidthPercentage) {
        setDrawerLeftEdgeSize(activity, drawerLayout, displayWidthPercentage, Gravity.LEFT);
        setDrawerLeftEdgeSize(activity, drawerLayout, displayWidthPercentage, Gravity.RIGHT);
    }

    public static void setDrawerLeftEdgeSize(@NonNull Activity activity, @NonNull DrawerLayout drawerLayout, double displayWidthPercentage, int gravityDic) {
        try {
            String draggerFieldName;
            String leftCallbackFieldName;
            if (gravityDic == Gravity.LEFT) {
                draggerFieldName = "mLeftDragger";
                leftCallbackFieldName = "mLeftCallback";
            } else if (gravityDic == Gravity.RIGHT) {
                draggerFieldName = "mRightDragger";
                leftCallbackFieldName = "mRightCallback";
            } else {
                throw new IllegalArgumentException("gravityDic must be Gravity.LEFT or Gravity.RIGHT");
            }
            Field declaredField = drawerLayout.getClass().getDeclaredField(draggerFieldName);
            declaredField.setAccessible(true);
            ViewDragHelper leftDragger = (ViewDragHelper) declaredField.get(drawerLayout);
            // find edgesize and set is accessible
            Field edgeSizeField = leftDragger.getClass().getDeclaredField("mDefaultEdgeSize");
            edgeSizeField.setAccessible(true);
            int edgeSize = edgeSizeField.getInt(leftDragger);
            Point displaySize = new Point();
            activity.getWindowManager().getDefaultDisplay().getSize(displaySize);
            edgeSizeField.setInt(leftDragger, Math.max(edgeSize, (int) (displaySize.x * displayWidthPercentage)));
            Field leftCallbackField = drawerLayout.getClass().getDeclaredField(leftCallbackFieldName);
            leftCallbackField.setAccessible(true);
            ViewDragHelper.Callback callback = (ViewDragHelper.Callback) leftCallbackField.get(drawerLayout);
            Field peekRunnableField = callback.getClass().getDeclaredField("mPeekRunnable");
            peekRunnableField.setAccessible(true);
            Runnable nullRunnable = new Runnable() {
                @Override
                public void run() {
                }
            };
            peekRunnableField.set(callback, nullRunnable);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
