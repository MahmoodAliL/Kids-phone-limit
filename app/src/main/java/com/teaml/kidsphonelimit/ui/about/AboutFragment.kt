package com.teaml.kidsphonelimit.ui.about

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.teaml.kidsphonelimit.BuildConfig
import com.teaml.kidsphonelimit.R
import com.teaml.kidsphonelimit.databinding.AboutFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AboutFragment : Fragment() {

    private val aboutViewModel: AboutViewModel by viewModel()

    private var _binding: AboutFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AboutFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupActionbarWithNavController()

        setAppVersion()

    }

    private fun setAppVersion() {
        binding.appVersionTv.text =
            String.format(resources.getString(R.string.version, BuildConfig.VERSION_NAME))
    }

    private fun setupActionbarWithNavController() {
        binding.toolbar.setupWithNavController(findNavController())
    }


    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
