package com.teaml.kidsphonelimit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavGraph
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.teaml.kidsphonelimit.utils.eventObserver
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onStart() {
        super.onStart()
        if (intent.extras?.getBoolean("lock") == true) {
            findNavController(R.id.nav_host_fragment).navigate(R.id.action_homeFragment_to_lockFragment)
        }
    }
}
