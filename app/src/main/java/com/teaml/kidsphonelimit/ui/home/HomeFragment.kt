package com.teaml.kidsphonelimit.ui.home

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.teaml.circulartimerview.CircularTimerListener
import com.teaml.circulartimerview.TimeFormatEnum

import com.teaml.kidsphonelimit.R
import com.teaml.kidsphonelimit.databinding.HomeFragmentBinding
import java.text.DecimalFormat
import kotlin.math.ceil

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel

    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!
    
    private var timeSelected = 1

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
        setupMinutePicker()
        setupCircularTimerView()
        
        binding.startTimerBtn.setOnClickListener { 
            startTimer(timeSelected)
            binding.minutePickerLayout.visibility = View.GONE
            binding.timerProgress.visibility = View.VISIBLE
        }

    }

    private fun setSupportActionBar() {
        (activity as? AppCompatActivity)?.setSupportActionBar(binding.toolbar)
    }

    private fun setupMinutePicker() {
        binding.minutePicker.setFormatter { value ->
            String.format("%02d", value)
        }
        
        binding.minutePicker.setOnValueChangedListener { _, _, newVal -> 
            timeSelected = newVal
        }
    }

    private fun setupCircularTimerView() {
        binding.timerProgress.progress = 0f
    }


    private fun startTimer(minute: Int) {

        with(binding) {

            timerProgress.progress = 0f
            timerProgress.setCircularTimerListener(
                CircularTimerListener1(), minute.toLong(),
                TimeFormatEnum.MINUTES, 1000
            )

            timerProgress.startTimer()
        }
        
    }

    inner class CircularTimerListener1 : CircularTimerListener {
        override fun updateDataOnTick(remainingTimeInMs: Long): String? {
            val decimalFormat = DecimalFormat("00")
            val second = ceil(remainingTimeInMs.div(1_000f)).toInt()
            Log.e("timer MS", remainingTimeInMs.toString())
            Log.e("timer SE", second.toString())
            val min = decimalFormat.format(second / 60)
            val sec = decimalFormat.format(second % 60)

            return "$min:$sec"
        }

        override fun onTimerFinished() {
            //Toast.makeText(context, "onTimerFinished", Toast.LENGTH_LONG).show()
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
