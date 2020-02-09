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
import com.teaml.kidsphonelimit.MainActivity
import com.teaml.kidsphonelimit.ON_EXIST_TAG
import com.teaml.kidsphonelimit.R
import com.teaml.kidsphonelimit.databinding.LockFragmentBinding
import com.teaml.kidsphonelimit.utils.setOnLongPressClick
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel

class LockFragment : Fragment() {


    companion object {
        const val TAG = "LockFragment"
    }

    private val lockViewModel: LockViewModel by viewModel()

    private val alarmManager: AlarmManager by currentScope.inject()
    private val notifyPendingIntent: PendingIntent by currentScope.inject()

    private var _binding: LockFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lockViewModel.lockPhone()
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

        binding.unlockImg.setOnLongPressClick(5_000) {
            lockViewModel.unlockPhone()
            findNavController().navigate(R.id.action_lockFragment_to_homeFragment)
        }

    }


    override fun onPause() {
        super.onPause()
        Log.d(ON_EXIST_TAG, "onPause LockFragment")

        if (lockViewModel.shouldLockPhone() && isScreenAwake()) {

            Log.d(ON_EXIST_TAG, "lockPhone")

            AlarmManagerCompat.setExactAndAllowWhileIdle(
                alarmManager,
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + 100L,
                notifyPendingIntent
            )
            MainActivity.exitApp(context!!)
            //ExitActivity.exitApp(context!!)

        }
    }

    private fun isScreenAwake(): Boolean {
        val powerManager = context!!.getSystemService(Context.POWER_SERVICE) as PowerManager
        return if (Build.VERSION.SDK_INT < 20) {
            powerManager.isScreenOn
        } else {
            powerManager.isInteractive
        }
    }

    override fun onStop() {
        super.onStop()
        Log.d(ON_EXIST_TAG, "onStop LockFragment ")

    }


    override fun onDestroy() {
        _binding = null
        super.onDestroy()
        Log.d(ON_EXIST_TAG, "onDestroy LockFragment ")
    }

}