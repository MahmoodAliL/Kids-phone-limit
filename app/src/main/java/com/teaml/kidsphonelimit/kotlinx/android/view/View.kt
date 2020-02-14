package com.teaml.kidsphonelimit.kotlinx.android.view

import android.view.MotionEvent
import android.view.View


fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.setOnLongPressClick(duration: Long, listener: () -> Unit) {

    setOnTouchListener { _, motionEvent ->

        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> {
                handler.postDelayed({
                    listener.invoke()
                }, duration)
            }

            MotionEvent.ACTION_UP -> {
                handler.removeCallbacksAndMessages(null)
            }
        }
        false
    }
}