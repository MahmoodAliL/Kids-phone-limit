package com.teaml.kidsphonelimit

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.os.SystemClock
import androidx.core.app.AlarmManagerCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.navigation.NavDeepLinkBuilder
import com.teaml.kidsphonelimit.data.repository.TimeRepository
import com.teaml.kidsphonelimit.di.appComponent
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(appComponent)
        }
    }

}