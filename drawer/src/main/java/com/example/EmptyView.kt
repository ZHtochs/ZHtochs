package com.example

import android.content.Context
import android.content.res.Configuration
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.example.drawer.R
import com.github.zhtouchs.Utils.ZHLog

class EmptyView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    LinearLayout(context, attrs, defStyleAttr) {


    private val emptyView: LinearLayout by lazy {
        findViewById(R.id.empty_view)
    }

    private val buttonContainer: FrameLayout by lazy {
        findViewById(R.id.button_container)
    }

    private val imageFl: FrameLayout by lazy {
        findViewById(R.id.image_fl)
    }

    var listener: View.OnClickListener? = null
        set(value) {
            ZHLog.d(TAG, "setListener")
            field = value
            findViewById<Button>(R.id.empty_button)?.setOnClickListener(value)
        }

    private val contentLl: LinearLayout by lazy {
        findViewById(R.id.content_ll)
    }

    init {
        changeLayoutOrientation()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        changeLayoutOrientation()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        ZHLog.d(TAG, "onFinishInflate")
        findViewById<Button>(R.id.empty_button).setOnClickListener(listener)
    }

    private fun changeLayoutOrientation() {
        ZHLog.d(TAG, "changeLayoutOrientation")
        if (ScreenUtils.isLandScape(context)) {
            removeAllViews()
            LayoutInflater.from(context).inflate(R.layout.empty_view_land, this, true)
                .findViewById<Button>(R.id.empty_button).setOnClickListener(listener)
        } else {
            removeAllViews()
            LayoutInflater.from(context).inflate(R.layout.empty_view, this, true)
                .findViewById<Button>(R.id.empty_button).setOnClickListener(listener)
        }
    }


    companion object {
        private const val TAG = "EmptyView"
    }


}