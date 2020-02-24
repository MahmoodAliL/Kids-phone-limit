package com.teaml.kidsphonelimit

import android.app.Application
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.teaml.kidsphonelimit.data.pref.Preferences
import com.teaml.kidsphonelimit.data.repository.AppRepository
import com.teaml.kidsphonelimit.di.appComponent
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.inject

class App : Application(), LifecycleObserver, KoinComponent {

    private val repository: AppRepository by inject()
    companion object {
        private const val TAG = "App"
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(appComponent)
        }


        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onAppBackgrounded() {
        repository.saveAppBackgroundState(true)
        Log.d(TAG, "onAppBackgrounded")
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onAppForegrounded() {
        repository.saveAppBackgroundState(false)
        Log.d(TAG, "onAppForegrounded")
    }

}