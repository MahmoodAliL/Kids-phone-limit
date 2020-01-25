package com.teaml.kidsphonelimit.ui.home

import android.os.Bundle
import android.text.SpannedString
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.teaml.circulartimerview.CircularTimerListener
import com.teaml.circulartimerview.TimeFormatEnum
import com.teaml.kidsphonelimit.R
import com.teaml.kidsphonelimit.databinding.HomeFragmentBinding
import com.teaml.kidsphonelimit.utils.hide
import com.teaml.kidsphonelimit.utils.show

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel

    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSupportActionBar()
        initMinutePicker()
        initCircularTimerView()

        binding.startTimerBtn.setOnClickListener {
            if (binding.timerProgress.isStarted) {
                stopTimer()
            } else {
                startTimer()
            }
        }

    }

    private fun setSupportActionBar() {
        (activity as? AppCompatActivity)?.setSupportActionBar(binding.toolbar)
    }

    private fun initMinutePicker() {
        binding.minutePicker.setFormatter { value ->
            String.format("%02d", value)
        }
    }

    private fun initCircularTimerView() {
        binding.timerProgress.progress = 0f
    }

    private fun stopTimer() {
        stopTimerProgress()
        updateUiToMinutePickerMode()
    }

    private fun stopTimerProgress() {
        binding.timerProgress.cancelTimer()
    }

    private fun updateUiToMinutePickerMode() {
        with(binding) {
            minutePickerLayout.show()
            timerProgress.hide()
            startTimerBtn.text = resources.getString(R.string.start_timer)
            titleTv.text = resources.getString(R.string.set_time_limit)
        }
    }

    private fun startTimer() {
        startTimerProgress()
        updateUiToTimerProgressMode()
    }

    private fun startTimerProgress() {
        with(binding) {
            timerProgress.progress = 0f
            timerProgress.setCircularTimerListener(
                CircularTimerListener1(),
                minutePicker.value.toLong(),
                TimeFormatEnum.MINUTES,
                100
            )
            timerProgress.startTimer()
        }
    }

    private fun updateUiToTimerProgressMode() {
        with(binding) {
            timerProgress.show()
            minutePickerLayout.hide()
            startTimerBtn.text = resources.getString(R.string.stop_timer)
            titleTv.text = resources.getString(R.string.timer_is_starting)
        }
    }


    inner class CircularTimerListener1 : CircularTimerListener {

        private fun Long.toSecond() = this.div(1_000)
        private fun Long.toMinute() = this.div(60)

        override fun updateDataOnTick(remainingTimeInMs: Long): String? {

            val remainingTimeInSecond = remainingTimeInMs.toSecond()
            val min = String.format("%02d", remainingTimeInSecond.toMinute())
            val sec = String.format("%02d", remainingTimeInSecond % 60)

            return "$min:$sec"
        }

        override fun onTimerFinished() {
            stopTimer()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }


}
