package com.example

import android.content.Context
import android.content.res.Configuration


object ScreenUtils {
    fun getScreenWidth(context: Context) = context.resources.displayMetrics.widthPixels

    fun getScreenHeight(context: Context) = context.resources.displayMetrics.heightPixels

    fun isLandScape(context: Context) =
        context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    fun isTablet(context: Context): Boolean {
        return (context.resources.configuration.screenLayout
                and Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE
    }

    fun dp2px(context: Context, dp: Int): Float {
        return context.resources.displayMetrics.density * dp + 0.5f
    }
}