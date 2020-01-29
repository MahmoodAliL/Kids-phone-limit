package com.teaml.kidsphonelimit.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.teaml.kidsphonelimit.data.repository.TimeRepository

class AlarmReceiver : BroadcastReceiver() {


    companion object {
        const val REQUEST_CODE = 0
    }

    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "Finished", Toast.LENGTH_LONG).show()

    }
}