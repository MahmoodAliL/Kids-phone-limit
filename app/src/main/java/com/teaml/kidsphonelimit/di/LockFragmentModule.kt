package com.teaml.kidsphonelimit.di

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.navigation.NavDeepLinkBuilder
import com.teaml.kidsphonelimit.MainActivity
import com.teaml.kidsphonelimit.R
import com.teaml.kidsphonelimit.receiver.AlarmReceiver
import com.teaml.kidsphonelimit.ui.home.HomeFragment
import com.teaml.kidsphonelimit.ui.home.HomeViewModel
import com.teaml.kidsphonelimit.ui.lock.LockFragment
import com.teaml.kidsphonelimit.ui.lock.LockViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val lockFragmentModule = module {

    viewModel { LockViewModel(get()) }

    scope(named<LockFragment>()) {

        scoped { Intent(androidContext(), AlarmReceiver::class.java) }

        scoped { androidContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager }

        scoped {
            PendingIntent.getBroadcast(
                androidContext(),
                AlarmReceiver.REQUEST_CODE,
                get(),
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }
    }
}