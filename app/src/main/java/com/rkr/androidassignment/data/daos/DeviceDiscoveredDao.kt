package com.rkr.androidassignment.data.daos

import androidx.room.*
import com.rkr.androidassignment.data.model.DeviceDiscovered
import kotlinx.coroutines.flow.Flow

@Dao
interface DeviceDiscoveredDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(deviceDiscovered: DeviceDiscovered)

    @Transaction
    @Query("SELECT * FROM devices")
    fun getAll(): Flow<List<DeviceDiscovered>>
}
