package com.teaml.kidsphonelimit.data.repository

import com.teaml.kidsphonelimit.data.pref.Preferences

class TimeRepository(private val preferences: Preferences): Preferences {

    override suspend fun saveTriggerTime(triggerTime: Long) {
        preferences.saveTriggerTime(triggerTime)
    }

    override suspend fun loadTriggerTime(): Long {
        return preferences.loadTriggerTime()
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