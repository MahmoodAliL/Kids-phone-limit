package com.teaml.kidsphonelimit.ui.home

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.AlarmManagerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import com.teaml.circulartimerview.CircularTimerListener
import com.teaml.circulartimerview.TimeFormatEnum
import com.teaml.kidsphonelimit.R
import com.teaml.kidsphonelimit.databinding.HomeFragmentBinding
import com.teaml.kidsphonelimit.receiver.AlarmReceiver
import com.teaml.kidsphonelimit.utils.*
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class HomeFragment : Fragment() {

    companion object {
        private const val TIMER_INTERVAL = 100L
    }

    private val homeViewModel: HomeViewModel by viewModel()

    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!

    private val alarmManager: AlarmManager by currentScope.inject()
    private val notifyPendingIntent: PendingIntent by currentScope.inject()


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSupportActionBar()
        initMinutePicker()

        binding.startTimerBtn.setOnClickListener {
            homeViewModel.updateTimerState()
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
            homeViewModel.setTimeSelected(newVal)
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        homeViewModel.isTimerOn.observe(viewLifecycleOwner) {
            if (it) updateUiToTimerProgressMode() else updateUiToMinutePickerMode()
        }

        homeViewModel.timerProgressLiveData.observe(viewLifecycleOwner) { (timeInMinute, progress) ->
            Log.e("HomeFragment", "minute  :$timeInMinute, progress : $progress")
            startTimerProgress(timeInMinute, progress)
        }

        homeViewModel.selectedTimeLiveData.observe(viewLifecycleOwner) { selectedTime ->
            binding.minutePicker.value = selectedTime
        }

        homeViewModel.startAlarmManager.eventObserver(viewLifecycleOwner) { triggerTime ->

            AlarmManagerCompat.setExactAndAllowWhileIdle(
                alarmManager,
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                triggerTime,
                notifyPendingIntent
            )
        }

        homeViewModel.stopAlarmManager.eventObserver(viewLifecycleOwner) {
            alarmManager.cancel(notifyPendingIntent)
        }
    }

    private fun updateUiToMinutePickerMode() {
        stopTimerProgress()
        with(binding) {
            minutePickerLayout.show()
            timerProgress.hide()
            startTimerBtn.text = resources.getString(R.string.start_timer)
            titleTv.text = resources.getString(R.string.set_time_limit)
        }
    }

    private fun stopTimerProgress() {
        binding.timerProgress.cancelTimer()
    }

    private fun startTimerProgress(time: Int, progress: Long) {
        with(binding) {
            timerProgress.setCircularTimerListener(
                CircularTimerListener1(),
                time.toLong(),
                TimeFormatEnum.SECONDS,
                TIMER_INTERVAL,
                progress
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

        override fun updateDataOnTick(remainingTimeInMs: Long): String? {

            val min = String.format("%02d", remainingTimeInMs.millisToMinute())
            val sec = String.format("%02d", remainingTimeInMs.millisToSecond() % 60)

            return "$min:$sec"
        }

        override fun onTimerFinished() {
            homeViewModel.onTimerFinished()
            Log.e("HomeFragment", "onTimerFinished")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController()) || super.onOptionsItemSelected(
            item
        )
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}
