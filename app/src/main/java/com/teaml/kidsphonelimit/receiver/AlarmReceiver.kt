package com.teaml.kidsphonelimit.receiver

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.navigation.NavDeepLinkBuilder
import com.teaml.kidsphonelimit.R
import com.teaml.kidsphonelimit.data.repository.AppRepository
import kotlinx.coroutines.*
import org.koin.androidx.scope.currentScope
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.qualifier.named

class AlarmReceiver : BroadcastReceiver(), KoinComponent {


    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val repository: AppRepository by inject()
    private val navDeepLinkBuilder: PendingIntent by inject()

    companion object {
        const val REQUEST_CODE = 0
        private const val TAG = "AlarmReceiver"
    }

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "onReceive")

        navDeepLinkBuilder.send()

        coroutineScope.launch {
            repository.saveTimerState(false)
            repository.saveLockState(true)
        }

    }
}