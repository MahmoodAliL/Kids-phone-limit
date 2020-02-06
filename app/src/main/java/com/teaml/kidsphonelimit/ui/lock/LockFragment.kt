package com.teaml.kidsphonelimit.ui.lock

import android.app.AlarmManager
import android.app.PendingIntent
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.AlarmManagerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.teaml.kidsphonelimit.R
import com.teaml.kidsphonelimit.databinding.LockFragmentBinding
import com.teaml.kidsphonelimit.utils.Event
import com.teaml.kidsphonelimit.utils.eventObserver
import com.teaml.kidsphonelimit.utils.setOnLongPressClick
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class LockFragment : Fragment() {


    companion object {
        const val TAG = "LockFragment"
    }

    private val lockViewModel: LockViewModel by viewModel()

    private val alarmManager: AlarmManager by currentScope.inject()
    private val notifyPendingIntent: PendingIntent by currentScope.inject()

    private var _binding: LockFragmentBinding? = null
    private val binding get() = _binding!!


    private val startAlarmManagerObserver = Observer<Event<Unit?>> {
        it?.let {


        }

    }


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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        lockViewModel.navigation.eventObserver(viewLifecycleOwner) {
            findNavController().navigateUp()
        }

        lockViewModel.startAlarmManager.eventObserver(viewLifecycleOwner) {
            Log.d(TAG, "onActivityCreated: startAlarmManager")
            AlarmManagerCompat.setExactAndAllowWhileIdle(
                alarmManager,
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + 100L,
                notifyPendingIntent
            )
        }
    }


    override fun onStop() {
        Log.d(TAG, "onStop: ")
        lockViewModel.shouldLockPhone()
        super.onStop()
    }


    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}