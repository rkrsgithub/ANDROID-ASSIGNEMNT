package com.rkr.androidassignment.di

import com.rkr.androidassignment.ui.MainActivity
import com.rkr.androidassignment.ui.detail.DetailFragment
import com.rkr.androidassignment.ui.device.DeviceFragment
import dagger.Component

@AppScope
@Component(
    modules = [AppModule::class, AppViewModelModule::class]
)
interface AppComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(fragment: DeviceFragment)
    fun inject(fragment: DetailFragment)

}
