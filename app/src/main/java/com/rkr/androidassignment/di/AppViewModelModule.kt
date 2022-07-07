package com.rkr.androidassignment.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rkr.androidassignment.ui.MainViewModel
import com.rkr.androidassignment.ui.detail.DetailViewModel
import com.rkr.androidassignment.ui.device.DeviceViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AppViewModelModule {


    @Binds
    abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelMapKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelMapKey(DeviceViewModel::class)
    abstract fun bindDeviceViewModel(viewModel: DeviceViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelMapKey(DetailViewModel::class)
    abstract fun bindDetailViewModel(viewModel: DetailViewModel): ViewModel

}
