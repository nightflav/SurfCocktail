package com.example.surfcocktailscompose.util

import android.app.Activity
import com.example.surfcocktailscompose.App
import com.example.surfcocktailscompose.di.AppComponent

fun Activity.getAppComponent(): AppComponent = (this.applicationContext as App).appComponent