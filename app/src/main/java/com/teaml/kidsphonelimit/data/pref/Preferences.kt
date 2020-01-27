package com.teaml.kidsphonelimit.data.pref

interface Preferences {

    fun saveTime(triggerTime: Long)

    fun loadTime(): Long
}