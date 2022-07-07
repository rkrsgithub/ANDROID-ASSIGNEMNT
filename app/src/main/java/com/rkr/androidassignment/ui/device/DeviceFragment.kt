package com.rkr.androidassignment.ui.device

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rkr.androidassignment.R
import com.rkr.androidassignment.di.AppComponentProvider
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

/**
 * A fragment representing a list of Items.
 */
class DeviceFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: DeviceViewModel by viewModels { viewModelFactory }
    private val deviceRecyclerViewAdapter: DeviceRecyclerViewAdapter = DeviceRecyclerViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as? AppComponentProvider)?.getAppComponent()?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_device_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = deviceRecyclerViewAdapter
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeDeviceList()
        setClickListener()
        observeNavigation()
    }

    private fun observeNavigation() {
        lifecycleScope.launchWhenCreated {
            viewModel.navigationListener.collectLatest {
                it?.let {
                    findNavController().navigate(it)
                }
            }
        }
    }

    private fun setClickListener() {
        deviceRecyclerViewAdapter.setOnItemClickListener { _, _, _ ->
            viewModel.onClickDevice()
        }
    }

    private fun observeDeviceList() {
        lifecycleScope.launchWhenCreated {
            viewModel.deviceList.collectLatest {
                deviceRecyclerViewAdapter.setValues(it)
            }
        }
    }

}
