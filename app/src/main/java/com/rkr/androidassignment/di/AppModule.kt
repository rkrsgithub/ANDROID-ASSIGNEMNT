package com.rkr.androidassignment.di

import android.app.Application
import android.content.Context
import com.rkr.androidassignment.data.AppDB
import com.rkr.androidassignment.data.daos.DeviceDiscoveredDao
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
class AppModule(private val application: Application) {

    @Reusable
    @Provides
    fun provideApplication(): Application {
        return application
    }

    @Reusable
    @Provides
    fun provideApplicationContext(): Context {
        return application.applicationContext
    }


    @Reusable
    @Provides
    fun provideAppDB(context: Context): AppDB {
        return AppDB.getInstance(context)
    }

    @Reusable
    @Provides
    fun provideDeviceDiscoveredDao(appDB: AppDB): DeviceDiscoveredDao {
        return appDB.deviceDiscoveredDao()
    }
}
