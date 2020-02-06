package com.teaml.kidsphonelimit.di

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.navigation.NavDeepLinkBuilder
import com.teaml.kidsphonelimit.R
import com.teaml.kidsphonelimit.data.pref.AppPreferences
import com.teaml.kidsphonelimit.data.pref.Preferences
import com.teaml.kidsphonelimit.data.repository.TimeRepository
import com.teaml.kidsphonelimit.receiver.AlarmReceiver
import com.teaml.kidsphonelimit.ui.home.HomeFragment
import com.teaml.kidsphonelimit.ui.home.HomeViewModel
import com.teaml.kidsphonelimit.utils.PREF_FILE_NAME
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.named
import org.koin.dsl.module

val homeFragmentModule = module {

    viewModel { HomeViewModel(get()) }

    scope(named<HomeFragment>()) {

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