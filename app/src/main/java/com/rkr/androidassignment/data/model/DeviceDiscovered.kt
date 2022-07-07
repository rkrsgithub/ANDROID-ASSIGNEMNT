package com.rkr.androidassignment.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data class that captures device information for logged in users retrieved from LoginRepository
 */
@Entity(tableName = "devices")
data class DeviceDiscovered(
    @PrimaryKey
    val hostAddress: String,
    val serviceName: String
)
