package com.teaml.kidsphonelimit.kotlinx.androix.lifecycle

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import com.teaml.kidsphonelimit.utils.Event


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
