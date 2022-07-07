package com.rkr.androidassignment.data

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rkr.androidassignment.data.daos.DeviceDiscoveredDao
import com.rkr.androidassignment.data.model.DeviceDiscovered

@Database(
    entities = [
        DeviceDiscovered::class
    ], version = 1, exportSchema = false
)
abstract class AppDB : RoomDatabase() {

    abstract fun deviceDiscoveredDao(): DeviceDiscoveredDao

    companion object {

        @VisibleForTesting
        private val DATABASE_NAME = "AppDB.db"

        private var sInstance: AppDB? = null

        fun getInstance(context: Context): AppDB {
            return sInstance ?: synchronized(this) {
                sInstance ?: buildDatabase(context.applicationContext).also { sInstance = it }
            }
        }

        /**
         * Build the database. only sets up the database configuration and
         * creates a new instance of the database.
         * The SQLite database is only created when it's accessed for the first time.
         */
        private fun buildDatabase(appContext: Context): AppDB {
            return Room.databaseBuilder(appContext, AppDB::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
