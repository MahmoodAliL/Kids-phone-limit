package com.teaml.kidsphonelimit.utils

import java.util.*

object TimeUtils {

    fun minuteToMillis(minute: Int): Long {
        return minute * 1_000L
    }

    private fun millisToSecond(millis: Long): Long {
        return millis.div(1_000)
    }

    private fun millisToMinute(millis: Long): Long {
        return millis.div(60_000)
    }

    fun formatElapsedTime(interval: Long, locale: Locale = Locale.getDefault()): String {

        val min: Long = millisToMinute(interval) % 60
        val sec: Long = millisToSecond(interval) % 60

        return String.format(locale, "%02d:%02d", min, sec)
    }
}