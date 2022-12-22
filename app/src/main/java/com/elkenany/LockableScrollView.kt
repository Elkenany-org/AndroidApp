package com.elkenany

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ScrollView


class LockableScrollView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : ScrollView(context, attrs) {
    private var mScrollable = true

    fun setScrollingEnabled(enabled: Boolean) {
        mScrollable = enabled
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return when (ev.action) {
            MotionEvent.ACTION_DOWN -> mScrollable && super.onTouchEvent(ev)
            else -> super.onTouchEvent(ev)
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return mScrollable && super.onInterceptTouchEvent(ev)
    }
}