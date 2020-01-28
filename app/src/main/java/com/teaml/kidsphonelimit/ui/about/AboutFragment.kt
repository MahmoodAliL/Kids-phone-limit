package com.teaml.kidsphonelimit.ui.about

import androidx.lifecycle.ViewModelProviders
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

class AboutFragment : Fragment() {

    companion object {
        fun newInstance() = AboutFragment()
    }

    private lateinit var viewModel: AboutViewModel

    private var _binding: AboutFragmentBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AboutViewModel::class.java)
    }

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
