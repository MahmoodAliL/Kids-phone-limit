package com.teaml.kidsphonelimit.di

import androidx.navigation.NavDeepLinkBuilder
import com.teaml.kidsphonelimit.R
import com.teaml.kidsphonelimit.ui.home.HomeViewModel
import com.teaml.kidsphonelimit.ui.lock.LockViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val lockFragmentModule = module {

    viewModel { LockViewModel(get()) }

    single {
        NavDeepLinkBuilder(androidContext())
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.lockFragment)
            .createPendingIntent()
    }
}