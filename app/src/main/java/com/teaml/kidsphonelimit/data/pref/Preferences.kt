package com.teaml.kidsphonelimit.data.pref

interface Preferences {

    suspend fun saveTime(triggerTime: Long)

    suspend fun loadTime(): Long

    suspend fun saveSelectedTimer(time: Int)

    suspend fun loadSelectedTimer(): Int

    suspend fun saveTimerState(state: Boolean)

    suspend fun loadTimerState(): Boolean
}