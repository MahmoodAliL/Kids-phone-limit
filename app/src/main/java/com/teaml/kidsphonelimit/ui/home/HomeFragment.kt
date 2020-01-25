package com.teaml.kidsphonelimit.ui.home

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.teaml.circulartimerview.CircularTimerListener
import com.teaml.circulartimerview.TimeFormatEnum

import com.teaml.kidsphonelimit.R
import kotlinx.android.synthetic.main.home_fragment.*
import java.text.DecimalFormat
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.round

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)

        minute_picker.setFormatter { value ->
            String.format("%02d", value)
        }

        timer_progress.progress = 0F
        timer_progress.setCircularTimerListener(object : CircularTimerListener {
            override fun updateDataOnTick(remainingTimeInMs: Long): String? {
                val decimalFormat = DecimalFormat("00")
                val second = ceil(remainingTimeInMs.div(1_000f)).toInt()
                Log.e("timer MS", remainingTimeInMs.toString())
                Log.e("timer SE" , second.toString())
                val min = decimalFormat.format(second / 60)
                val sec = decimalFormat.format(second % 60)

                return "$min:$sec"
            }

            override fun onTimerFinished() {
                Toast.makeText(context, "onTimerFinished", Toast.LENGTH_LONG).show()
            }
        }, 1, TimeFormatEnum.MINUTES,10)
        timer_progress.startTimer()

        /*   recycler_view.adapter = MinutesAdapter()
           recycler_view.layoutManager = LinearLayoutManager(context)*/
    }

    private fun setTime() {

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


}
