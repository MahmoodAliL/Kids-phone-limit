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


    override fun onHandleIntent(intent: Intent?) {

        while (repository.loadLockState()) {
            Log.d(TAG, "onHandleIntent: inside loop")
            if (shouldOpenLockScreen()) {
                lockScreenPendingIntent.send()
                Log.d(TAG, "onHandleIntent: sendPendingIntent")
            }
            Thread.sleep(5_000)
        }

        Log.d(TAG, "onHandleIntent: endLoop")
    }


    private fun shouldOpenLockScreen(): Boolean {
        return repository.loadAppBackgroundState() && ScreenUtils.isScreenAwake(applicationContext)
    }


}