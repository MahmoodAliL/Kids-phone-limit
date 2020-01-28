package com.teaml.kidsphonelimit.ui.home

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
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
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        viewModel.setTimeSelected(1)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSupportActionBar()
        initMinutePicker()
        initCircularTimerView()

        binding.startTimerBtn.setOnClickListener {
            viewModel.setTimerState(binding.timerProgress.isStarted)
        }
    }

    private fun setSupportActionBar() {
        (activity as? AppCompatActivity)?.setSupportActionBar(binding.toolbar)
    }

    private fun initMinutePicker() {
        binding.minutePicker.setFormatter { value ->
            String.format("%02d", value)
        }
        binding.minutePicker.setOnValueChangedListener { _, _, newVal ->
            viewModel.setTimeSelected(newVal)
        }
    }

    private fun initCircularTimerView() {
        binding.timerProgress.progress = 50f
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.stopTimerLiveData.observe(viewLifecycleOwner) {
            stopTimer()
        }

        viewModel.startTimerLiveData.observe(viewLifecycleOwner) { time ->
            startTimer(time.toLong())
        }

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

    private fun startTimer(time: Long) {
        startTimerProgress(time)
        updateUiToTimerProgressMode()
    }

    private fun startTimerProgress(time: Long) {
        with(binding) {
            timerProgress.progress = 50f


            timerProgress.setCircularTimerListener(
                CircularTimerListener1(),
                time,
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController()) || super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}
