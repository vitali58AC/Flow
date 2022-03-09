package com.example.flow

import android.app.Application
import com.example.flow.data.database.Database
import timber.log.Timber.*
import timber.log.Timber.Forest.plant


class FlowApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        //timber init
        if (BuildConfig.DEBUG) {
            plant(DebugTree())
        }
        Database.init(this)
    }
}