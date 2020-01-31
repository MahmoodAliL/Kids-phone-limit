package com.teaml.kidsphonelimit.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.teaml.kidsphonelimit.data.repository.TimeRepository
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class AlarmReceiver : BroadcastReceiver(), KoinComponent {


    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val repository: TimeRepository by inject()

    companion object {
        const val REQUEST_CODE = 0
        private const val TAG = "AlarmReceiver"
    }

    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "Finished", Toast.LENGTH_LONG).show()


        Log.d(TAG, "onReceive")
        coroutineScope.launch {
            repository.saveTimerState(false)
        }

    }



}