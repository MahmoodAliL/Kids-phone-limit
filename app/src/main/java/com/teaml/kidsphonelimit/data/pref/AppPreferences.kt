package com.teaml.kidsphonelimit.data.pref

import android.content.Context
import androidx.core.content.edit

class AppPreferences(context: Context, fileName: String) : Preferences {

    companion object {
        const val PREF_KEY_TRIGGER_TIME = "PREF_KEY_TRIGGER_TIME"
        const val PREF_KEY_SELECTED_TIMER_LENGTH = "PREF_KEY_SELECTED_TIMER_LENGTH"
        const val PREF_KEY_TIMER_STATE = "PREF_KEY_TIMER_STATE"
        const val PREF_KEY_LOCK_STATE = "PREF_KEY_LOCK_STATE"
        const val PREF_KEY_APP_BACKGROUND_STATE = "PREF_KEY_APP_BACKGROUND_STATE"
    }

    private val prefs = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)

    override suspend fun saveTriggerTime(triggerTime: Long) {
        prefs.edit { putLong(PREF_KEY_TRIGGER_TIME, triggerTime) }
    }

    override suspend fun loadTriggerTime(): Long {
        return prefs.getLong(PREF_KEY_TRIGGER_TIME, 0)
    }


    override suspend fun saveTimeSelected(time: Int) {
        prefs.edit { putInt(PREF_KEY_SELECTED_TIMER_LENGTH, time) }
    }

    override suspend fun loadTimeSelected(): Int {
        return prefs.getInt(PREF_KEY_SELECTED_TIMER_LENGTH, 0)
    }

    override suspend fun saveTimerState(state: Boolean) {
        prefs.edit { putBoolean(PREF_KEY_TIMER_STATE, state) }
    }

    override suspend fun loadTimerState(): Boolean {
        return prefs.getBoolean(PREF_KEY_TIMER_STATE, false)
    }

    override fun saveLockState(state: Boolean) {
        prefs.edit { putBoolean(PREF_KEY_LOCK_STATE, state) }
    }

    override fun loadLockState() = prefs.getBoolean(PREF_KEY_LOCK_STATE, false)

    override fun saveAppBackgroundState(state: Boolean) {
        prefs.edit { putBoolean(PREF_KEY_APP_BACKGROUND_STATE, state) }
    }

    override fun loadAppBackgroundState(): Boolean {
        return prefs.getBoolean(PREF_KEY_APP_BACKGROUND_STATE, false)
    }
}