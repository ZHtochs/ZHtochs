package com.example

import android.content.Context
import android.content.res.Configuration
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView
import com.github.zhtouchs.Utils.ZHLog

public class CustomImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    private val phoneWidth: Float by lazy { ScreenUtils.dp2px(context, 40) }

    private val phoneWidthLandscape: Float by lazy { ScreenUtils.dp2px(context, 60) }

    private val padWidth: Float by lazy { ScreenUtils.dp2px(context, 80) }


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        ensureWidth()
        ZHLog.d(TAG, "onAttachedToWindow")
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            ZHLog.d(TAG, "event x ${it.x} event y ${it.y}")
            ZHLog.d(TAG, " x $x y $y")
            ZHLog.d(TAG, " x $width y $height")


        }
        performClick()
        return super.onTouchEvent(event)
    }


    private fun ensureWidth() {
        ZHLog.d(TAG, "ensureWidth")
        if (ScreenUtils.isLandScape(context)) {
            layoutParams.width = phoneWidthLandscape.toInt()
            layoutParams.height = phoneWidthLandscape.toInt()
        } else if (ScreenUtils.isTablet(context)) {
            layoutParams.width = padWidth.toInt()
            layoutParams.height = padWidth.toInt()
        } else {
            layoutParams.height = phoneWidth.toInt()
            layoutParams.width = phoneWidth.toInt()
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
//        ensureWidth()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        ensureWidth()
    }

    companion object {
        private const val TAG = "CustomImageView"
    }
}