package com.rkr.androidassignment.ui.device

import androidx.lifecycle.ViewModel
import com.rkr.androidassignment.data.DeviceRepository
import javax.inject.Inject

class DeviceViewModel @Inject constructor(
    deviceRepository: DeviceRepository
) : ViewModel() {

    val deviceList = deviceRepository.getAll()
}
