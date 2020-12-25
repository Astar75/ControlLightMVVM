package com.astar.osterrig.controllightmvvm.app

import android.app.Application
import android.content.Context
import com.astar.osterrig.controllightmvvm.di.application
import com.astar.osterrig.controllightmvvm.di.mainScreen
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class OsterrigApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@OsterrigApplication)
            modules(listOf(
                application, mainScreen
            ))

        }
    }
}