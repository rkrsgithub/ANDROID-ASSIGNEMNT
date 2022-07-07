package com.rkr.androidassignment.ui.discovery

import android.content.Context
import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.rkr.androidassignment.R


class DiscoveryFragment : Fragment() {

    companion object {
        const val TAG = "Discovery"
    }

    private lateinit var viewModel: DiscoveryViewModel
    private var mServiceName: String? = null
    private var nsdManager: NsdManager? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_discovery, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[DiscoveryViewModel::class.java]
        // TODO: Use the ViewModel
    }

    private fun discoverServices() {
        nsdManager = (activity?.getSystemService(Context.NSD_SERVICE) as NsdManager).apply {
//            discoverServices("_ipp._tcp", NsdManager.PROTOCOL_DNS_SD, discoveryListener)
            discoverServices("_mdns._tcp", NsdManager.PROTOCOL_DNS_SD, discoveryListener)
        }
    }

    // Instantiate a new DiscoveryListener
    private val discoveryListener = object : NsdManager.DiscoveryListener {

        // Called as soon as service discovery begins.
        override fun onDiscoveryStarted(regType: String) {
            Log.d(TAG, "Service discovery started - $regType")
        }

        override fun onServiceFound(service: NsdServiceInfo) {
            // A service was found! Do something with it.
            Log.d(TAG, "Service discovery success$service")
            when {
                service.serviceType != "SERVICE_TYPE" -> //TODO Service type is the string containing the protocol and
                    // transport layer for this service.
                    Log.d(TAG, "Unknown Service Type: ${service.serviceType}")
                service.serviceName == mServiceName -> // The name of the service tells the user what they'd be
                    // connecting to. It could be "Bob's Chat App".
                    Log.d(TAG, "Same machine: $mServiceName")
                else -> {
                    Log.d(TAG, "onServiceFound: ${service.serviceName}")
                }
            }
        }

        override fun onServiceLost(service: NsdServiceInfo) {
            // When the network service is no longer available.
            // Internal bookkeeping code goes here.
            Log.e(TAG, "service lost: $service")
        }

        override fun onDiscoveryStopped(serviceType: String) {
            Log.i(TAG, "Discovery stopped: $serviceType")
        }

        override fun onStartDiscoveryFailed(serviceType: String, errorCode: Int) {
            Log.e(TAG, "Discovery failed: Error code:$errorCode")
            nsdManager?.stopServiceDiscovery(this)
        }

        override fun onStopDiscoveryFailed(serviceType: String, errorCode: Int) {
            Log.e(TAG, "Discovery failed: Error code:$errorCode")
            nsdManager?.stopServiceDiscovery(this)
        }
    }

    //In your application's Activity

    override fun onPause() {
        tearDown()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        discoverServices()
    }

    override fun onDestroy() {
        tearDown()
        super.onDestroy()
    }

    // NsdHelper's tearDown method
    private fun tearDown() {
        nsdManager?.apply {
            stopServiceDiscovery(discoveryListener)
        }
    }

}
