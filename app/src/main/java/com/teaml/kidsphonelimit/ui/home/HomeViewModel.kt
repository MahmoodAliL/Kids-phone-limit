package com.teaml.kidsphonelimit.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.teaml.kidsphonelimit.data.repository.TimeRepository

class HomeViewModel(private val repository: TimeRepository) : ViewModel() {


    private val _stopTimer = MutableLiveData<Unit>()
    val stopTimerLiveData: LiveData<Unit> get() = _stopTimer

    private val _startTimer = MutableLiveData<Int>()
    val startTimerLiveData: LiveData<Int> get() = _startTimer

    private var selectedTime = 0

    fun setTimerState(isStarted: Boolean) {
        when(isStarted) {
            true -> _stopTimer.value = null
            false -> _startTimer.value = selectedTime
        }
    }

    fun setTimeSelected(time: Int) {
        selectedTime = time
    }

    fun startTimer(timerLengthSelection: Int) {

    }



}
