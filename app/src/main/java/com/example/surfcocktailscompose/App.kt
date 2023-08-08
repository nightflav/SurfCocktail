package com.example.surfcocktailscompose

import android.app.Application
import com.example.surfcocktailscompose.di.AppComponent
import com.example.surfcocktailscompose.di.DaggerAppComponent

class App : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
    }
}