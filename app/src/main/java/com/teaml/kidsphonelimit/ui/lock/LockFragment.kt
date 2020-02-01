package com.teaml.kidsphonelimit.ui.lock

import android.app.PendingIntent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.teaml.kidsphonelimit.databinding.LockFragmentBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class LockFragment : Fragment() {

    private val lockViewModel: LockViewModel by viewModel()
    private val deepNavPendingIntent: PendingIntent by inject()

    private var _binding: LockFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lockViewModel.saveTimerState(false)
        lockViewModel.enableLock()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =  LockFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.stopButton.setOnClickListener {
            lockViewModel.disableLock()
            findNavController().navigateUp()
        }

    }

    override fun onStop() {
        super.onStop()
        lockViewModel.sendPendingIntentIfLockEnable(deepNavPendingIntent)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}