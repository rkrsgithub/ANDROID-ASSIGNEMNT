package com.rkr.androidassignment.data

import com.rkr.androidassignment.data.model.DeviceDiscovered
import javax.inject.Inject

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class DeviceRepository @Inject constructor(private val deviceDataSource: DeviceDataSource) {

    suspend fun insert(deviceDiscovered: DeviceDiscovered) =
        deviceDataSource.insert(deviceDiscovered)

    fun getAll() = deviceDataSource.getAll()
}
