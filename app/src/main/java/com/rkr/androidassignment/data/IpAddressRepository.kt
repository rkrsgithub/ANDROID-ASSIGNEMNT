package com.rkr.androidassignment.data

import javax.inject.Inject

class IpAddressRepository @Inject constructor(private val ipAddressDataSource: IpAddressDataSource) {

    suspend fun getDetailsOfCurrentIpAddress() = ipAddressDataSource.getIpAddressDetails()
}
