package com.example.sensor.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.RecyclerView
import com.example.sensor.R
import com.example.sensor.databinding.FragmentSensorBinding
import com.example.sensor.utils.Globals
import com.example.sensor.view.AdapterSensor
import com.example.sensor.view.MainActivity
import com.example.sensor.viewmodel.SensorsViewModel


class SensorsFragment : Fragment() {
    private val viewModel: SensorsViewModel by viewModels()

    private lateinit var recyclerHome: RecyclerView
    private lateinit var adapterSensor: AdapterSensor

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSensorBinding.inflate(inflater, container, false)

        val toolbar: Toolbar = binding.includeToolbarHome.toolbarHome
        (requireActivity() as MainActivity).setSupportActionBar(toolbar)

        val navController = NavHostFragment.findNavController(this)
        val drawerLayout: DrawerLayout =
            (requireActivity() as MainActivity).findViewById(R.id.drawer_layout)
        NavigationUI.setupActionBarWithNavController(
            requireActivity() as MainActivity,
            navController,
            drawerLayout
        )

        recyclerHome = binding.recyclerHome
        adapterSensor = AdapterSensor(requireActivity())
        recyclerHome.adapter = adapterSensor

        recyclerHome.recycledViewPool.setMaxRecycledViews(-1, 0)
        recyclerHome.setItemViewCacheSize(Globals.sensors.size)

        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.primary)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSensors()
    }

    override fun onStart() {
        super.onStart()
        viewModel.registerListeners()
    }

    override fun onPause() {
        viewModel.unregisterListeners()
        super.onPause()
    }

    private fun initSensors() {
        adapterSensor.submitList(viewModel.sensors)
    }
}