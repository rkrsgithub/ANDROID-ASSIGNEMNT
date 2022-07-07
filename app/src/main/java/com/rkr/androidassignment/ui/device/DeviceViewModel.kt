package com.rkr.androidassignment.ui.device

import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.rkr.androidassignment.data.DeviceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class DeviceViewModel @Inject constructor(
    deviceRepository: DeviceRepository
) : ViewModel() {

    private val _navigationListener = MutableStateFlow<NavDirections?>(null)
    val navigationListener: StateFlow<NavDirections?> = _navigationListener

    val deviceList = deviceRepository.getAll()

    fun onClickDevice() {
        _navigationListener.tryEmit(DeviceFragmentDirections.actionDetailFragment())
        _navigationListener.tryEmit(null)
    }

}
