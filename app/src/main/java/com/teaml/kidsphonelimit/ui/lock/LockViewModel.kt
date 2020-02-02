package com.teaml.kidsphonelimit.ui.lock

import android.app.PendingIntent
import android.content.Intent
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

}