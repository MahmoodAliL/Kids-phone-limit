package com.teaml.kidsphonelimit.ui.lock

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.teaml.kidsphonelimit.R
import com.teaml.kidsphonelimit.databinding.LockFragmentBinding
import com.teaml.kidsphonelimit.utils.eventObserver
import com.teaml.kidsphonelimit.utils.setOnLongPressClick
import kotlinx.android.synthetic.main.lock_fragment.view.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class LockFragment : Fragment() {

    private val lockViewModel: LockViewModel by viewModel()
    //private val deepNavPendingIntent: PendingIntent by inject()
    private val alarmManager: AlarmManager by inject()
    private val notifyPendingIntent: PendingIntent by inject()

    private var _binding: LockFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lockViewModel.enableLock()
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
            lockViewModel.disableLock()
            findNavController().navigate(R.id.action_lockFragment_to_homeFragment)
            //lockViewModel.navigateUp()
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        lockViewModel.navigation.eventObserver(viewLifecycleOwner) {
            findNavController().navigateUp()
        }
    }



    override fun onStop() {
        super.onStop()
        //lockViewModel.sendPendingIntentIfLockEnable(deepNavPendingIntent)
        lockViewModel.setAlarmReceiver(alarmManager, notifyPendingIntent)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}