package com.rkr.androidassignment.di

import com.rkr.androidassignment.ui.MainActivity
import dagger.Component

@AppScope
@Component(
    modules = [AppModule::class, AppViewModelModule::class]
)
interface AppComponent {

    fun inject(mainActivity: MainActivity)

}
