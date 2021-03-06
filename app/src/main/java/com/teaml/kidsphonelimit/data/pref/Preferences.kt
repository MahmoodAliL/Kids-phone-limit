package com.teaml.kidsphonelimit.data.pref

interface Preferences {

    suspend fun saveTriggerTime(triggerTime: Long)

    suspend fun loadTriggerTime(): Long

    suspend fun saveTimeSelected(time: Int)

    suspend fun loadTimeSelected(): Int

    suspend fun saveTimerState(state: Boolean)

    suspend fun loadTimerState(): Boolean

    fun saveLockState(state: Boolean)

    fun loadLockState(): Boolean

    fun saveAppBackgroundState(state: Boolean)

    fun loadAppBackgroundState(): Boolean

}