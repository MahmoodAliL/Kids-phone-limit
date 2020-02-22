package com.teaml.kidsphonelimit.service

import android.app.IntentService
import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import com.teaml.kidsphonelimit.App
import com.teaml.kidsphonelimit.ON_EXIST_TAG
import com.teaml.kidsphonelimit.data.repository.AppRepository
import com.teaml.kidsphonelimit.utils.ScreenUtils
import org.koin.core.KoinComponent
import org.koin.core.inject


class LockPhoneIntentService : IntentService("LockIntentService"), KoinComponent {

    companion object {
        private const val TAG = "LockPhoneIntentService"
    }

    private val lockScreenPendingIntent: PendingIntent by inject()
    private val repository: AppRepository by inject()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onHandleIntent(intent: Intent?) {
        Log.d(ON_EXIST_TAG, "onHandleIntent")

        val app = ( applicationContext as App)


        while (repository.loadLockState()) {
            Thread.sleep(5_000)
            Log.d(TAG, "onHandleIntent: inside loop")
            // TODO clean if statement
            if (app.isAppInBackground && ScreenUtils.isScreenAwake(applicationContext)) {
                Log.d(TAG, "onHandleIntent: sendPendingIntent")
                lockScreenPendingIntent.send()
            }
        }

        Log.d(TAG, "onHandleIntent: endLoop")
    }


    private fun shouldOpenLockScreen() {

    }


}