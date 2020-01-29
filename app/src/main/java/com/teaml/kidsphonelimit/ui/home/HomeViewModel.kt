package com.teaml.kidsphonelimit.ui.home

import android.os.SystemClock
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teaml.kidsphonelimit.data.repository.TimeRepository
import com.teaml.kidsphonelimit.utils.Event
import com.teaml.kidsphonelimit.utils.minuteToMillis
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: TimeRepository) : ViewModel() {

    private val _timerOn = MutableLiveData<Boolean>(false)
    val isTimerOn: LiveData<Boolean> get() = _timerOn

    private val _selectedTimer = MutableLiveData<Int>()
    val selectedTimeLiveData: LiveData<Int> get() = _selectedTimer

    private val _timerProgress = MutableLiveData<Pair<Int, Long>>()
    val timerProgressLiveData: LiveData<Pair<Int, Long>> = _timerProgress

    private val _startAlarmManager = MutableLiveData<Event<Long>>()
    val startAlarmManager: LiveData<Event<Long>> get() = _startAlarmManager

    private val _stopAlarmManager = MutableLiveData<Event<Unit>>()
    val stopAlarmManager: LiveData<Event<Unit>> get() = _stopAlarmManager


    private var selectedTime = 1

    init {
        viewModelScope.launch {
            _timerOn.value = repository.loadTimerState()
            updateTimerProgress()
        }
    }

    fun updateTimerState() {

        when (_timerOn.value) {
            true -> stopTimer()
            false -> startTimer()
        }
    }

    fun setTimeSelected(time: Int) {
        _selectedTimer.value = time
        selectedTime = time
    }

    private fun startTimer() {
        _timerOn.value?.let { isTimerOn ->

            if (!isTimerOn) {
                _timerOn.value = true

                val triggerTime =
                    SystemClock.elapsedRealtime() + selectedTime.minuteToMillis()

                _startAlarmManager.value = Event(triggerTime)

                saveTimerState(true)
                saveTime(triggerTime)
                saveSelectedTimeLength(selectedTime)

            }

            updateTimerProgress()
        }

    }

    private fun saveTimerState(state: Boolean) {
        viewModelScope.launch {
            repository.saveTimerState(state)
        }
    }

    private fun saveTime(triggerTime: Long) {
        viewModelScope.launch { repository.saveTime(triggerTime) }
    }

    private fun updateTimerProgress() {
        viewModelScope.launch {
            val selectedTime = repository.loadSelectedTimer()
            val triggerTime = repository.loadTime()

            var elapsedTime = triggerTime - SystemClock.elapsedRealtime()
            elapsedTime = selectedTime.minuteToMillis() - elapsedTime

            _timerProgress.value = selectedTime to elapsedTime

        }
    }

    private fun saveSelectedTimeLength(time: Int) {
        viewModelScope.launch { repository.saveSelectedTimer(time) }
    }


    fun onTimerFinished() {
        _timerOn.value = false
    }

    private fun stopTimer() {
        _timerOn.value = false
        saveTimerState(false)
        _stopAlarmManager.value = Event(Unit)
    }

}
