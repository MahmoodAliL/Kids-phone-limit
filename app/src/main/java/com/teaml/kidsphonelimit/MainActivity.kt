package com.teaml.kidsphonelimit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.teaml.kidsphonelimit.databinding.ActivityMainBinding
import com.teaml.kidsphonelimit.kotlinx.androix.lifecycle.eventObserver
import com.teaml.kidsphonelimit.service.LockPhoneIntentService
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        stopService()

        mainViewModel.openLockFragment.eventObserver(this) {
            Log.e(TAG, "Navigate to LockFragment")
            //findNavController(R.id.nav_host_fragment).navigate(R.id.lockFragment)
        }
    }

    private fun stopService() {
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

