package com.teaml.kidsphonelimit.ui.lock

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.AlarmManagerCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.teaml.kidsphonelimit.ExitActivity
import com.teaml.kidsphonelimit.ON_EXIST_TAG
import com.teaml.kidsphonelimit.databinding.LockFragmentBinding
import com.teaml.kidsphonelimit.utils.eventObserver
import com.teaml.kidsphonelimit.utils.setOnLongPressClick
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel

class LockFragment : Fragment() {


    companion object {
        const val TAG = "LockFragment"
        private const val TIME_DURATION: Long = 5_000
        private const val TIME_INTERVAL: Long = 100
    }

    private var _binding: LockFragmentBinding? = null
    private val binding get() = _binding!!

    private val lockViewModel: LockViewModel by viewModel()
    private val alarmManager: AlarmManager by currentScope.inject()
    private val notifyPendingIntent: PendingIntent by currentScope.inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lockViewModel.setPhoneStateAsLock()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LockFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.unlockImg.setOnLongPressClick(TIME_DURATION) {
            lockViewModel.setPhoneStateAsUnlock()
            lockViewModel.navigateUp()
        }
    }


    override fun onPause() {
        super.onPause()
        if (lockViewModel.shouldLockPhone() && isScreenAwake()) {
            setAlarmManager()
            ExitActivity.exitApp(context!!)
        }
    }

    private fun isScreenAwake(): Boolean {
        val powerManager = context!!.getSystemService(Context.POWER_SERVICE) as PowerManager
        return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            powerManager.isInteractive
        } else {
            powerManager.isScreenOn
        }
    }

    private fun setAlarmManager() {
        AlarmManagerCompat.setExactAndAllowWhileIdle(
            alarmManager,
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime() + TIME_INTERVAL,
            notifyPendingIntent
        )
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        lockViewModel.navigation.eventObserver(viewLifecycleOwner) {
            findNavController().navigateUp()
        }

    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}