package com.teaml.kidsphonelimit

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

const val ON_EXIST_TAG = "onExist"

class ExitActivity : AppCompatActivity() {

    companion object {

        fun exitApp(context: Context) {
            val intent = Intent(context, ExitActivity::class.java)

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)


            context.startActivity(intent)
            Log.d(ON_EXIST_TAG, "startExistActivity")
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(ON_EXIST_TAG, "onExistActivityCreated")
        if (Build.VERSION.SDK_INT >= 21) {
            finishAndRemoveTask()
            Log.d(ON_EXIST_TAG, "finishAndRemoveTask")
        } else {
            finish()
            Log.d(ON_EXIST_TAG, "finish")
        }
    }

    override fun onStop() {
        super.onStop()
        Log.d(ON_EXIST_TAG, "OnStop ExistActivity")
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.d(ON_EXIST_TAG, "onDestroy ExistActivity")
    }
}