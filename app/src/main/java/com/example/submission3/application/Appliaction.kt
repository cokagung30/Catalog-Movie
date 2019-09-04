package com.example.submission3.application

import android.app.Application
import com.example.submission3.sqlite.AppDatabase
import com.facebook.stetho.Stetho

class Appliaction : Application() {

    override fun onCreate() {
        super.onCreate()
        AppDatabase.createDatabase(this)
        Stetho.initializeWithDefaults(this)
    }

}