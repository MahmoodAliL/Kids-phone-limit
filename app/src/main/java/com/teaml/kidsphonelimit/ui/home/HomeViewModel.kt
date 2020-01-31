package com.teaml.kidsphonelimit.ui.home

import android.os.SystemClock
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teaml.kidsphonelimit.data.repository.TimeRepository
import com.teaml.kidsphonelimit.utils.Event
import com.teaml.kidsphonelimit.utils.TimeUtils
import com.teaml.kidsphonelimit.utils.getValueOrDefault
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: TimeRepository) : ViewModel() {

    private val _timerOn = MutableLiveData<Boolean>(false)
    val isTimerOn: LiveData<Boolean> get() = _timerOn

    private val _timeSelected = MutableLiveData<Int>()
    val timeSelected: LiveData<Int> get() = _timeSelected

    private val _timerProgress = MutableLiveData<Pair<Int, Long>>()
    val timerProgress: LiveData<Pair<Int, Long>> = _timerProgress

    private val _startAlarmManager = MutableLiveData<Event<Long>>()
    val startAlarmManager: LiveData<Event<Long>> get() = _startAlarmManager

    private val _stopAlarmManager = MutableLiveData<Event<Unit>>()
    val stopAlarmManager: LiveData<Event<Unit>> get() = _stopAlarmManager


    init {
        viewModelScope.launch {
            _timerOn.value = repository.loadTimerState()
        }
    }

    fun toggleTimerState() {
        when (_timerOn.value) {
            true -> stopTimer()
            false -> startTimer()
        }
    }

    fun setTimeSelected(time: Int) {
        _timeSelected.value = time
    }

    private fun startTimer() {

        if (isTimerOff()) {
            val triggerTime = calculateTriggerTime()
            startAlarmManager(triggerTime)
            saveCurrentTimerInfo(triggerTime, _timeSelected.getValueOrDefault())

            _timerOn.value = true
        }
    }

    private fun isTimerOff(): Boolean {
        return _timerOn.value == false
    }

    private fun calculateTriggerTime(): Long {
        val timeSelectedInMillis = TimeUtils.minuteToMillis(_timeSelected.getValueOrDefault())
        return SystemClock.elapsedRealtime() + timeSelectedInMillis
    }


    private fun startAlarmManager(triggerTime: Long) {
        _startAlarmManager.value = Event(triggerTime)
    }

    private fun saveCurrentTimerInfo(triggerTime: Long, timeSelected: Int) {
        saveTimerState(true)
        viewModelScope.launch {
            repository.saveTimeSelected(timeSelected)
            repository.saveTriggerTime(triggerTime)
        }
    }

    private fun saveTimerState(state: Boolean) {
        viewModelScope.launch { repository.saveTimerState(state) }
    }

    fun updateTimerProgress() {
        viewModelScope.launch {
            val timeSelected = repository.loadTimeSelected()
            val triggerTime = repository.loadTriggerTime()

            val elapsedTime = calculateElapsedTime(triggerTime, timeSelected)
            _timerProgress.postValue(timeSelected to elapsedTime)

        }
    }

    private fun calculateElapsedTime(triggerTime: Long, timeSelected: Int): Long {
        val remainingTimeInMillis = triggerTime - SystemClock.elapsedRealtime()
        val timeSelectedInMillis = TimeUtils.minuteToMillis(timeSelected)
        return timeSelectedInMillis - remainingTimeInMillis
    }


    private fun stopTimer() {
        _timerOn.value = false
        saveTimerState(false)
        stopAlarmManager()
    }

    private fun stopAlarmManager() {
        _stopAlarmManager.value = Event(Unit)
    }

}
