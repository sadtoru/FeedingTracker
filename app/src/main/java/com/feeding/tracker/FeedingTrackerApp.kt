package com.feeding.tracker

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FeedingTrackerApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
