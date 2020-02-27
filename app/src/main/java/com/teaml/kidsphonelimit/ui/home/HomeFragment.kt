package com.teaml.kidsphonelimit.ui.home

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
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
import com.teaml.kidsphonelimit.kotlinx.android.view.hide
import com.teaml.kidsphonelimit.kotlinx.android.view.show
import com.teaml.kidsphonelimit.kotlinx.androix.lifecycle.eventObserver
import com.teaml.kidsphonelimit.utils.TimeUtils
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel

import java.util.*


class HomeFragment : Fragment() {

    companion object {
        private const val TIMER_INTERVAL = 100L
        private const val TAG = "HomeFragment"
    }


    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModel()
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
            homeViewModel.toggleTimerState()
        }
    }

    private fun setSupportActionBar() {
        (activity as? AppCompatActivity)?.setSupportActionBar(binding.toolbar)
    }

    private fun initMinutePicker() {
        binding.minutePicker.setFormatter { value ->
            String.format(Locale.ENGLISH, "%02d", value)
        }

        binding.minutePicker.setOnValueChangedListener { _, _, newVal ->
            homeViewModel.setTimeSelected(newVal)
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        homeViewModel.isTimerOn.observe(viewLifecycleOwner) {
            if (it) {
                updateUiToTimerProgressMode()
            } else {
                updateUiToMinutePickerMode()
            }
        }

        homeViewModel.timerProgress.observe(viewLifecycleOwner) { (timeSelected, elapsedTime) ->
            startTimerProgress(timeSelected, elapsedTime)
        }

        homeViewModel.timeSelected.observe(viewLifecycleOwner) { selectedTime ->
            binding.minutePicker.value = selectedTime
        }

        homeViewModel.startAlarmManager.eventObserver(viewLifecycleOwner) { triggerTime ->
            // TODO maybe foreground notification will be more suitable for our case
            AlarmManagerCompat.setExactAndAllowWhileIdle(
                alarmManager,
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                triggerTime,
                notifyPendingIntent
            )
            Log.d(TAG, "onActivityCreated: startAlarmManager")
        }

        homeViewModel.stopAlarmManager.eventObserver(viewLifecycleOwner) {
            alarmManager.cancel(notifyPendingIntent)
            Log.d(TAG, "onActivityCreated: stopAlarmManager")
        }

        homeViewModel.openLockFragment.eventObserver(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_homeFragment_to_lockFragment)
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
        homeViewModel.updateTimerProgress()
        with(binding) {
            timerProgress.show()
            minutePickerLayout.hide()
            startTimerBtn.text = resources.getString(R.string.stop_timer)
            titleTv.text = resources.getString(R.string.timer_is_starting)
        }
    }


    inner class CircularTimerListener1 : CircularTimerListener {

        override fun updateDataOnTick(remainingTimeInMs: Long): String? {
            return TimeUtils.formatElapsedTime(remainingTimeInMs, Locale.ENGLISH)
        }

        override fun onTimerFinished() {

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
