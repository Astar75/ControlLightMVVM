package com.astar.osterrig.controllightmvvm

import android.app.Application
import android.content.Context

class OsterrigApplication : Application() {


    override fun onCreate() {
        super.onCreate()
    }

    companion object {

        lateinit var instance: Context

        fun getAppContext(): Context {
            instance = OsterrigApplication()
            return instance
        }
    }
}