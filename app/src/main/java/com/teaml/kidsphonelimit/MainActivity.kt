package com.teaml.kidsphonelimit

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.teaml.kidsphonelimit.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    companion object {
        fun exitApp(context: Context) {
            val intent = Intent(context, MainActivity::class.java)

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)

            intent.putExtra("exist", true)

            context.startActivity(intent)
        }
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*if (intent.getBooleanExtra("exist", false)) {
            if (Build.VERSION.SDK_INT >= 21) {
                finishAndRemoveTask()
                Log.d(ON_EXIST_TAG, "finishAndRemoveTask")
            } else {
                finish()
                Log.d(ON_EXIST_TAG, "finish")
            }
        }*/

    }

    /*override fun onStart() {
        super.onStart()
        if (intent.extras?.getBoolean("lock") == true) {
            findNavController(R.id.nav_host_fragment).navigate(R.id.action_homeFragment_to_lockFragment)
        }
        Log.d(ON_EXIST_TAG, "onStart MainActivity")
    }*/

    override fun onStop() {
        super.onStop()
        Log.d(ON_EXIST_TAG, "OnStop MainActivity")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(ON_EXIST_TAG, "onDestroy MainActivity")
    }



    override fun onBackPressed() {
        val currentFragment = findNavController(R.id.nav_host_fragment).currentDestination?.id
        if (currentFragment == R.id.lockFragment) {
            return
        }
        super.onBackPressed()
    }
}

