package com.teaml.kidsphonelimit.ui.lock

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teaml.kidsphonelimit.data.repository.AppRepository
import com.teaml.kidsphonelimit.utils.Event
import kotlinx.coroutines.launch

class LockViewModel(
    private val appRepository: AppRepository
) : ViewModel() {

    private val _navigation = MutableLiveData<Event<Unit>>()
    val navigation: LiveData<Event<Unit>> get() = _navigation

    fun setPhoneStateAsUnlock() {
        appRepository.saveLockState(false)
    }

    fun setPhoneStateAsLock() {
        appRepository.saveLockState(true)
    }

    fun shouldLockPhone(): Boolean {
         return appRepository.loadLockState()
    }

    fun navigateUp() {
        _navigation.value = Event(Unit)
    }
}