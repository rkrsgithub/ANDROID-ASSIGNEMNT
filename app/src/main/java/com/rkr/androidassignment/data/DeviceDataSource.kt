package com.rkr.androidassignment.data

import com.rkr.androidassignment.data.daos.DeviceDiscoveredDao
import com.rkr.androidassignment.data.model.DeviceDiscovered
import javax.inject.Inject

/**
 * Class that handles save and retrieve devices from local data base
 */
class DeviceDataSource @Inject constructor(private val deviceDiscoveredDao: DeviceDiscoveredDao) {

    suspend fun insert(deviceDiscovered: DeviceDiscovered) {
        deviceDiscoveredDao.insert(deviceDiscovered)
    }

    fun getAll() = deviceDiscoveredDao.getAll()
}
