package com.teaml.kidsphonelimit.utils

object TimeUtils {

    fun minuteToMillis(minute: Int): Long {
        return minute * 1_000L
    }

    fun millisToSecond(millis: Long): Long {
        return millis.div(1_000)
    }

    fun millisToMinute(millis: Long): Long {
        return millis.div(60_000)
    }
}