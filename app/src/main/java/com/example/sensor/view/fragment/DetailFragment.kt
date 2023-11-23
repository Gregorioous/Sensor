package com.example.sensor.view.fragment

import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.sensor.R
import com.example.sensor.databinding.FragmentDetailBinding
import com.example.sensor.viewmodel.DetailViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class DetailFragment : Fragment() {
    private val viewModel: DetailViewModel by viewModels()

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!


    private lateinit var name: TextView
    private lateinit var vendor: TextView
    private lateinit var version: TextView
    private lateinit var power: TextView
    private lateinit var maxDelay: TextView
    private lateinit var minDelay: TextView
    private lateinit var maxRange: TextView
    private lateinit var info: TextView
    private lateinit var xText: TextView
    private lateinit var yText: TextView
    private lateinit var zText: TextView


    private var looperJob: Job? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        val toolbar = binding.detail.toolbar
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        val navController = NavHostFragment.findNavController(this)
        NavigationUI.setupActionBarWithNavController(
            requireActivity() as AppCompatActivity,
            navController
        )


        name = binding.detail.name
        vendor = binding.detail.vendor
        version = binding.detail.version
        power = binding.detail.power
        maxDelay = binding.detail.maxDelay
        minDelay = binding.detail.minDelay
        maxRange = binding.detail.maxRange
        info = binding.detail.textInformation


        xText = binding.detail.xValDetail
        yText = binding.detail.yValDetail
        zText = binding.detail.zValDetail

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        looperJob = viewLifecycleOwner.lifecycleScope.launch {
            while (true) {
                updateMultiChart()
                delay(16)
            }
        }
        name.text = getString(R.string.labelName, viewModel.sensor.name)
        vendor.text = getString(R.string.labelVendor, viewModel.sensor.vendor)
        version.text = getString(R.string.labelVersion, viewModel.sensor.version.toString())
        power.text = getString(R.string.labelPower, viewModel.sensor.power.toString())
        maxDelay.text = getString(R.string.labelMaxDelay, viewModel.sensor.maxDelay.toString())
        minDelay.text = getString(R.string.labelMinDelay, viewModel.sensor.minDelay.toString())
        maxRange.text = getString(
            R.string.labelMaxRange,
            getString(viewModel.itemSensor.unit, viewModel.sensor.maximumRange)
        )
        info.text = getString(viewModel.itemSensor.info)

        if (viewModel.itemSensor.axes == 3) {
            yText.visibility = View.VISIBLE
            zText.visibility = View.VISIBLE
        }

        viewModel.itemSensor.let {
            val color = ContextCompat.getColor(requireContext(), it.color)
            val layout = binding.detail.toolbarLayout
            val icon = binding.detail.appBarImage

            layout.title = getString(it.name)
            layout.setBackgroundColor(color)
            layout.setContentScrimColor(color)
            activity?.window?.statusBarColor = color
            icon.setImageDrawable(ContextCompat.getDrawable(requireContext(), it.icon))
        }
    }

    private fun updateMultiChart() {
        xText.text = getString(
            viewModel.itemSensor.unit,
            viewModel.xValues.lastOrNull() ?: 0.0
        )
        yText.text = getString(
            viewModel.itemSensor.unit,
            viewModel.yValues.lastOrNull() ?: 0.0
        )
        zText.text = getString(
            viewModel.itemSensor.unit,
            viewModel.zValues.lastOrNull() ?: 0.0
        )
    }

    override fun onStart() {
        super.onStart()

        viewModel.sensorManager.registerListener(
            viewModel.sensorEventListener,
            viewModel.sensor,
            SensorManager.SENSOR_DELAY_UI
        )
    }


    override fun onPause() {
        looperJob?.cancel()
        super.onPause()
    }

    override fun onStop() {
        viewModel.sensorManager.unregisterListener(viewModel.sensorEventListener)
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}