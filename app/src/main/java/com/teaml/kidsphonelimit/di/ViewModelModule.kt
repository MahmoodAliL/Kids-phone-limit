package com.teaml.kidsphonelimit.di

import com.teaml.kidsphonelimit.ui.about.AboutViewModel
import com.teaml.kidsphonelimit.ui.home.HomeViewModel
import com.teaml.kidsphonelimit.ui.lock.LockViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { HomeViewModel(get()) }
    viewModel { LockViewModel(get()) }
    viewModel { AboutViewModel() }

}