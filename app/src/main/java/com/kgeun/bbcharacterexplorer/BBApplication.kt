package com.kgeun.bbcharacterexplorer

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BBApplication : Application() {
    companion object {
        lateinit var instance: BBApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this@BBApplication
    }
}