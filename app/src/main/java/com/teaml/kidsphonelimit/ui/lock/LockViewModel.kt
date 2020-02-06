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

    private val _startAlarmManager = MutableLiveData<Event<Unit>>()
    val startAlarmManager: LiveData<Event<Unit>> get() = _startAlarmManager

    fun saveTimerState(state: Boolean) {
        viewModelScope.launch { timeRepository.saveTimerState(state) }
    }

    fun unlockPhone() {
        timeRepository.saveLockState(false)
    }

    fun lockPhone() {
        timeRepository.saveLockState(true)
    }

    fun shouldLockPhone() {

        val isLock = timeRepository.loadLockState()
        if (isLock) {
            //_startAlarmManager.value = Event(Unit)
        }

    }

    fun navigateUp() {
        _navigation.value = Event(Unit)
    }
}