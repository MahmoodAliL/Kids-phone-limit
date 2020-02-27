package com.teaml.kidsphonelimit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.teaml.kidsphonelimit.data.repository.AppRepository
import com.teaml.kidsphonelimit.utils.Event

class MainViewModel(
    private val repository: AppRepository
) : ViewModel() {

    private val _openLockFragment = MutableLiveData<Event<Unit>>()
    val openLockFragment: LiveData<Event<Unit>> get() = _openLockFragment

    init {
        shouldStartLockFragment()
    }

    private fun shouldStartLockFragment() {
        if (repository.loadLockState()) {
            _openLockFragment.value = Event(Unit)
        }
    }



}