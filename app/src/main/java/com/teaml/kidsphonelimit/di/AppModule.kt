package com.teaml.kidsphonelimit.di

import androidx.navigation.NavDeepLinkBuilder
import com.teaml.kidsphonelimit.R
import com.teaml.kidsphonelimit.data.pref.AppPreferences
import com.teaml.kidsphonelimit.data.pref.Preferences
import com.teaml.kidsphonelimit.data.repository.AppRepository
import com.teaml.kidsphonelimit.receiver.AlarmReceiver
import com.teaml.kidsphonelimit.utils.PREF_FILE_NAME
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    single { AppPreferences(androidContext(), PREF_FILE_NAME) as Preferences }
    single { AppRepository(get()) }

    single {
        NavDeepLinkBuilder(androidContext())
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.lockFragment)
            .createPendingIntent()
    }
}
