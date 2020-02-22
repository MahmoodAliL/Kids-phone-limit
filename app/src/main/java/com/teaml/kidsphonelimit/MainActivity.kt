package com.teaml.kidsphonelimit

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.teaml.kidsphonelimit.databinding.ActivityMainBinding
import com.teaml.kidsphonelimit.service.LockPhoneIntentService


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Intent(this, LockPhoneIntentService::class.java).also {
            stopService(it)
        }
    }


    override fun onBackPressed() {
        val currentFragment = findNavController(R.id.nav_host_fragment).currentDestination?.id
        if (currentFragment == R.id.lockFragment) {
            return
        }
        super.onBackPressed()
    }
}

