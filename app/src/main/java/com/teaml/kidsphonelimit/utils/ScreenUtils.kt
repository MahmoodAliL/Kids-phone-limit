package com.teaml.kidsphonelimit.utils

import android.content.Context
import android.os.Build
import android.os.PowerManager

object ScreenUtils {
    fun isScreenAwake(context: Context): Boolean {
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            powerManager.isInteractive
        } else {
            powerManager.isScreenOn
        }
    }
}