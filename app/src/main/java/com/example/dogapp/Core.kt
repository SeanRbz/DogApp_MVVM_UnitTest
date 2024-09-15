package com.example.dogapp

import android.app.Application
import androidx.lifecycle.LifecycleObserver
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Core: Application(), LifecycleObserver {

    override fun onCreate() {
        super.onCreate()
    }
}