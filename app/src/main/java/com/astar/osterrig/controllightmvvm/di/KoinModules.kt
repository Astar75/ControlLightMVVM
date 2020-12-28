package com.astar.osterrig.controllightmvvm.di

import android.content.Context
import com.astar.osterrig.controllightmvvm.model.datasource.bluetooth_scanner.BluetoothScannerDataSourceImplementation
import com.astar.osterrig.controllightmvvm.model.datasource.persistence.DeviceModelDataSourceImplementation
import com.astar.osterrig.controllightmvvm.model.datasource.persistence.AppDatabase
import com.astar.osterrig.controllightmvvm.model.repository.DeviceModelRepository
import com.astar.osterrig.controllightmvvm.model.repository.DeviceModelRepositoryImplementation
import com.astar.osterrig.controllightmvvm.view.screen_devices.DeviceListInteractorImplementation
import com.astar.osterrig.controllightmvvm.view.screen_devices.DeviceListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val application = module {

    fun provideDatabase(context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    single { provideDatabase(androidContext()) }
    single<DeviceModelRepository>(named(NAME_LOCAL)) {
        DeviceModelRepositoryImplementation(
            DeviceModelDataSourceImplementation(get()),
            BluetoothScannerDataSourceImplementation()
        )
    }
}

val mainScreen = module {
    //factory { MainInteractor(get(named(NAME_LOCAL))) }
    //factory { MainViewModel(get()) }
    factory { DeviceListInteractorImplementation(get(named(NAME_LOCAL))) }
    factory { DeviceListViewModel(get()) }
}