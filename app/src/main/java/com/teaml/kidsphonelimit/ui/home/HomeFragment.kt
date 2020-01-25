package com.teaml.kidsphonelimit.ui.home

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.teaml.circulartimerview.CircularTimerListener
import com.teaml.circulartimerview.TimeFormatEnum
import com.teaml.kidsphonelimit.R
import com.teaml.kidsphonelimit.databinding.HomeFragmentBinding

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
            with(binding) {
                if (startTimerBtn.tag == "start") {
                    startTimerBtn.tag = "stop"

                    startTimer(binding.minutePicker.value)
                    binding.minutePickerLayout.visibility = View.GONE
                    binding.timerProgress.visibility = View.VISIBLE
                    binding.startTimerBtn.text = resources.getString(R.string.stop_timer)

                } else if (startTimerBtn.tag == "stop") {
                    startTimerBtn.tag = "start"

                    cancelTimer()
                    binding.minutePickerLayout.visibility = View.VISIBLE
                    binding.timerProgress.visibility = View.GONE
                    binding.startTimerBtn.text = resources.getString(R.string.start_timer)
                }
            }
        }

    }

    private fun setSupportActionBar() {
        (activity as? AppCompatActivity)?.setSupportActionBar(binding.toolbar)
    }

    private fun setupMinutePicker() {
        binding.minutePicker.setFormatter { value ->
            String.format("%02d", value)
        }
    }

    private fun setupCircularTimerView() {
        binding.timerProgress.progress = 0f
    }


    private fun startTimer(minute: Int) {

        with(binding) {

            timerProgress.progress = 0f
            timerProgress.setCircularTimerListener(
                CircularTimerListener1(),
                minute.toLong(),
                TimeFormatEnum.MINUTES,
                100
            )

            timerProgress.startTimer()
        }

    }

    private fun cancelTimer() {
        binding.timerProgress.cancelTimer()
    }

    inner class CircularTimerListener1 : CircularTimerListener {

        override fun updateDataOnTick(remainingTimeInMs: Long): String? {

            val remainingTimeInSecond = remainingTimeInMs.toSecond()
            val min = String.format("%02d", remainingTimeInSecond.toMinute())
            val sec = String.format("%02d", remainingTimeInSecond % 60)

            return "$min:$sec"
        }

        private fun Long.toSecond() = this.div(1_000)
        private fun Long.toMinute() = this.div(60)


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
