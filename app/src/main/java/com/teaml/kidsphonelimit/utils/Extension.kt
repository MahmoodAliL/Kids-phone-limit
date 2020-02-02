package com.teaml.kidsphonelimit.utils

import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.*

fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.setOnLongPressClick(duration: Long, listener: () -> Unit) {

    setOnTouchListener { _, motionEvent ->
        val handler = Handler()

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

fun LiveData<Int>.getValueOrDefault(default: Int = 1): Int {
    return this.value ?: default
}

/**
 * An [Observer] for [Event]s, simplifying the pattern of checking if the [Event]'s content has
 * already been handled.
 *
 * [onEventUnhandledContent] is *only* called if the [Event]'s contents has not been handled.
 */
inline fun <T> LiveData<Event<T>>.eventObserver(
    owner: LifecycleOwner,
    crossinline onEventUnhandledContent: (T) -> Unit
) {
    observe(owner) { event: Event<T> ->
        event.getContentIfNotHandled()?.let { onEventUnhandledContent(it) }
    }
}

