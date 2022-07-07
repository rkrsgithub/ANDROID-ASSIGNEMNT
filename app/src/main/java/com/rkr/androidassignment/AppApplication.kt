package com.rkr.androidassignment

import android.app.Application
import com.rkr.androidassignment.di.AppComponent
import com.rkr.androidassignment.di.AppComponentProvider
import com.rkr.androidassignment.di.AppModule
import com.rkr.androidassignment.di.DaggerAppComponent

class AppApplication : Application(), AppComponentProvider {
    override fun getAppComponent(): AppComponent {
        return DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}
