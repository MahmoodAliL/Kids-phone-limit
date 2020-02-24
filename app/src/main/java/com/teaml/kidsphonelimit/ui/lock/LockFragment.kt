package com.teaml.kidsphonelimit.ui.lock

import android.app.ActivityManager
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.teaml.kidsphonelimit.ExitActivity
import com.teaml.kidsphonelimit.databinding.LockFragmentBinding
import com.teaml.kidsphonelimit.kotlinx.android.view.setOnLongPressClick
import com.teaml.kidsphonelimit.kotlinx.androix.lifecycle.eventObserver
import com.teaml.kidsphonelimit.service.LockPhoneIntentService
import com.teaml.kidsphonelimit.utils.ScreenUtils
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel


class LockFragment : Fragment() {


    companion object {
        const val TAG = "LockFragment"
        private const val LONG_PRESS_TIME_DURATION: Long = 5_000
    }

    private var _binding: LockFragmentBinding? = null
    private val binding get() = _binding!!

    private val lockViewModel: LockViewModel by viewModel()

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

        binding.unlockImg.setOnLongPressClick(LONG_PRESS_TIME_DURATION) {
            lockViewModel.setPhoneStateAsUnlock()
            lockViewModel.navigateUp()
        }
    }


    override fun onPause() {
        super.onPause()
        val isScreenAwake = ScreenUtils.isScreenAwake(context!!)
        if (lockViewModel.shouldLockPhone() and isScreenAwake) {
            finishAndRemoveTask()
        }
    }


    private fun finishAndRemoveTask() {
        val am = activity!!.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val appTasks = am.appTasks
            if (appTasks.size > 0) {
                val appTask = appTasks[0]
                appTask.finishAndRemoveTask()
            }
        } else {
            ExitActivity.exitApp(context!!)
        }
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