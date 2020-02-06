package com.teaml.kidsphonelimit.di

import com.teaml.kidsphonelimit.data.pref.AppPreferences
import com.teaml.kidsphonelimit.data.pref.Preferences
import com.teaml.kidsphonelimit.data.repository.TimeRepository
import com.teaml.kidsphonelimit.utils.PREF_FILE_NAME
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {

    single { AppPreferences(androidContext(), PREF_FILE_NAME) as Preferences }
    single { TimeRepository(get()) }


}