package com.teaml.kidsphonelimit.receiver

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.navigation.NavDeepLinkBuilder
import com.teaml.kidsphonelimit.MainActivity
import com.teaml.kidsphonelimit.R
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
        Log.d(TAG, "onReceive")

        val pendingIntent: PendingIntent = NavDeepLinkBuilder(context)
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.lockFragment)
            .createPendingIntent()
        pendingIntent.send()

        coroutineScope.launch {
            repository.saveTimerState(false)
            repository.saveLockState(true)
        }

    }


}