package com.teaml.kidsphonelimit.receiver

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.teaml.kidsphonelimit.data.repository.AppRepository
import com.teaml.kidsphonelimit.service.LockPhoneIntentService
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class AlarmReceiver : BroadcastReceiver(), KoinComponent {


    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val repository: AppRepository by inject()
    private val navDeepLinkPendingIntent: PendingIntent by inject()

    companion object {
        const val REQUEST_CODE = 0
        private const val TAG = "AlarmReceiver"
    }

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "onReceive")

        repository.saveLockState(true)

        Intent(context, LockPhoneIntentService::class.java).also {
            context.startService(it)
        }

        coroutineScope.launch {
            repository.saveTimerState(false)
        }

    }
}