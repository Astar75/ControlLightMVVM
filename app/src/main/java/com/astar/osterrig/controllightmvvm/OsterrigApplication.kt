package com.astar.osterrig.controllightmvvm

import android.app.Application
import android.content.Context
import com.astar.osterrig.controllightmvvm.di.application
import com.astar.osterrig.controllightmvvm.di.mainScreen
import org.koin.core.context.startKoin

class OsterrigApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(listOf(
                application, mainScreen
            ))
        }
    }
}