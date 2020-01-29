package com.teaml.kidsphonelimit.utils

/**
 * Used as a wrapper for data that is exposed via a LiveData that represent an event
 */
open class Event<out T>(private val content: T) {


    var hasBeenHandled = false
        private set


    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    fun peekContent(): T = content
}