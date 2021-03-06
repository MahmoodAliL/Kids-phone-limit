package com.teaml.kidsphonelimit.data.repository

import com.teaml.kidsphonelimit.data.pref.Preferences

class AppRepository(private val preferences: Preferences): Preferences {

    override suspend fun saveTriggerTime(triggerTime: Long) {
        preferences.saveTriggerTime(triggerTime)
    }

    override suspend fun loadTriggerTime(): Long {
        return preferences.loadTriggerTime()
    }

    override suspend fun saveTimeSelected(time: Int) {
        preferences.saveTimeSelected(time)
    }

    override suspend fun loadTimeSelected(): Int {
        return preferences.loadTimeSelected()
    }

    override suspend fun saveTimerState(state: Boolean) {
        preferences.saveTimerState(state)
    }

    override suspend fun loadTimerState(): Boolean {
        return preferences.loadTimerState()
    }

    override  fun saveLockState(state: Boolean) {
        preferences.saveLockState(state)
    }

    override fun loadLockState(): Boolean {
        return preferences.loadLockState()
    }

    override fun saveAppBackgroundState(state: Boolean) {
        preferences.saveAppBackgroundState(state)
    }

    override fun loadAppBackgroundState(): Boolean {
        return preferences.loadAppBackgroundState()
    }
}