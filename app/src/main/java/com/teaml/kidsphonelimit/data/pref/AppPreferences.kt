package com.teaml.kidsphonelimit.data.pref

import android.content.Context
import androidx.core.content.edit

class AppPreferences(context: Context, fileName: String) : Preferences {

    companion object {
        const val PREF_KEY_TRIGGER_TIME = "PREF_KEY_TRIGGER_TIME"
    }

    private val prefs = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)

    override suspend fun saveTime(triggerTime: Long) {
        prefs.edit { putLong(PREF_KEY_TRIGGER_TIME, triggerTime) }
    }

    override suspend fun loadTime(): Long = prefs.getLong(PREF_KEY_TRIGGER_TIME, 0)

}