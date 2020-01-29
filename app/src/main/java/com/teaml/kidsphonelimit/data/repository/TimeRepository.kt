package com.teaml.kidsphonelimit.data.repository

import com.teaml.kidsphonelimit.data.pref.Preferences

class TimeRepository(private val preferences: Preferences): Preferences {

    override suspend fun saveTime(triggerTime: Long) {
        preferences.saveTime(triggerTime)
    }

    override suspend fun loadTime(): Long {
        return preferences.loadTime()
    }

    override suspend fun saveSelectedTimer(time: Int) {
        preferences.saveSelectedTimer(time)
    }

    override suspend fun loadSelectedTimer(): Int {
        return preferences.loadSelectedTimer()
    }

    override suspend fun saveTimerState(state: Boolean) {
        preferences.saveTimerState(state)
    }

    override suspend fun loadTimerState(): Boolean {
        return preferences.loadTimerState()
    }


}