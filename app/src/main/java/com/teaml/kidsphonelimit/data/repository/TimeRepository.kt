package com.teaml.kidsphonelimit.data.repository

import com.teaml.kidsphonelimit.data.pref.Preferences

class TimeRepository(private val preferences: Preferences): Preferences {

    override fun saveTime(triggerTime: Long) {
        preferences.saveTime(triggerTime)
    }

    override fun loadTime(): Long {
        return preferences.loadTime()
    }

}