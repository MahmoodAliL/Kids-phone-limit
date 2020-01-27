package com.teaml.kidsphonelimit.data.pref

interface Preferences {

    suspend fun saveTime(triggerTime: Long)

    suspend fun loadTime(): Long
}