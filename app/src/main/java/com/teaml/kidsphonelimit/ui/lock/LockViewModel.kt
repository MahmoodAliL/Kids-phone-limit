package com.teaml.kidsphonelimit.ui.lock

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import androidx.core.app.AlarmManagerCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teaml.kidsphonelimit.data.repository.TimeRepository
import com.teaml.kidsphonelimit.utils.Event
import kotlinx.coroutines.launch

class LockViewModel(
    private val timeRepository: TimeRepository
) : ViewModel() {

    private val _navigation = MutableLiveData<Event<Unit>>()
    val navigation: LiveData<Event<Unit>> get() = _navigation

    fun saveTimerState(state: Boolean) {
        viewModelScope.launch { timeRepository.saveTimerState(state) }
    }

    fun disableLock() {
        viewModelScope.launch { timeRepository.saveLockState(false) }
    }

    fun enableLock() {
        viewModelScope.launch { timeRepository.saveLockState(true) }
    }

    fun sendPendingIntentIfLockEnable(pendingIntent: PendingIntent) {
        viewModelScope.launch {
            val isLock = timeRepository.loadLockState()
            if (isLock) {
                pendingIntent.send()
            }
        }
    }

    fun setAlarmReceiver(alarmManager: AlarmManager, pendingIntent: PendingIntent) {
        viewModelScope.launch {
            val isLock = timeRepository.loadLockState()
            if (isLock) {
                AlarmManagerCompat.setExactAndAllowWhileIdle(
                    alarmManager,
                    AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    1000L,
                    pendingIntent
                )
            }
        }
    }

    fun navigateUp() {
        _navigation.value = Event(Unit)
    }
}