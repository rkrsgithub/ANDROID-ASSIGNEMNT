package com.rkr.androidassignment.ui

import android.net.nsd.NsdServiceInfo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rkr.androidassignment.data.DeviceRepository
import com.rkr.androidassignment.data.model.DeviceDiscovered
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val deviceRepository: DeviceRepository
) : ViewModel() {

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    fun saveDevice(serviceInfo: NsdServiceInfo) {
        viewModelScope.launch {
            serviceInfo.host.hostAddress
                ?.let { DeviceDiscovered(it, serviceInfo.serviceName) }
                ?.let { deviceRepository.insert(it) }
        }
    }

    fun setLoggedInState(loggedIn: Boolean) {
        _isLoggedIn.tryEmit(loggedIn)
    }
}
