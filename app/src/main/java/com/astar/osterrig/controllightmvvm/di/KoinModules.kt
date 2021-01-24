package com.astar.osterrig.controllightmvvm.di

import android.content.Context
import com.astar.osterrig.controllightmvvm.model.datasource.bluetoothscanner.BluetoothScannerDataSourceImplementation
import com.astar.osterrig.controllightmvvm.model.datasource.persistence.DeviceModelDataSourceImplementation
import com.astar.osterrig.controllightmvvm.model.datasource.persistence.AppDatabase
import com.astar.osterrig.controllightmvvm.model.repository.DeviceModelRepository
import com.astar.osterrig.controllightmvvm.model.repository.DeviceModelRepositoryImplementation
import com.astar.osterrig.controllightmvvm.view.NavigationManager
import com.astar.osterrig.controllightmvvm.view.NavigationManagerImplementation
import com.astar.osterrig.controllightmvvm.view.screen_cct_control.CctControlViewModel
import com.astar.osterrig.controllightmvvm.view.screen_devices.DeviceListInteractorImplementation
import com.astar.osterrig.controllightmvvm.view.screen_devices.DeviceListViewModel
import com.astar.osterrig.controllightmvvm.view.screen_fnc_wals_control.FncWalsControlViewModel
import com.astar.osterrig.controllightmvvm.view.screen_fnc_rgb_control.FncRgbControlViewModel
import com.astar.osterrig.controllightmvvm.view.screen_ftp_control.FtpControlViewModel
import com.astar.osterrig.controllightmvvm.view.screen_ftp_rgb_control.FtpRgbControlViewModel
import com.astar.osterrig.controllightmvvm.view.screen_groups.GroupListInteractorImplementation
import com.astar.osterrig.controllightmvvm.view.screen_groups.GroupListViewModel
import com.astar.osterrig.controllightmvvm.view.screen_rgb_control.RgbControlViewModel
import com.astar.osterrig.controllightmvvm.view.screen_tc_control.TcControlViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val application = module {

    fun provideDatabase(context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    fun provideNavigationManager(): NavigationManager {
        return NavigationManagerImplementation.newInstance()
    }

    single { provideDatabase(androidContext()) }
    single<DeviceModelRepository>(named(NAME_LOCAL)) {
        DeviceModelRepositoryImplementation(
            DeviceModelDataSourceImplementation(get()),
            BluetoothScannerDataSourceImplementation()
        )
    }

    single { provideNavigationManager() }
}

val mainScreen = module {
    //factory { MainInteractor(get(named(NAME_LOCAL))) }
    //factory { MainViewModel(get()) }
    factory { DeviceListInteractorImplementation(get(named(NAME_LOCAL))) }
    factory { GroupListInteractorImplementation(get(named(NAME_LOCAL))) }
    viewModel { DeviceListViewModel(get()) }
    viewModel { GroupListViewModel(get()) }
    viewModel { RgbControlViewModel() }
    viewModel { CctControlViewModel() }
    viewModel { FtpControlViewModel() }
    viewModel { FtpRgbControlViewModel() }
    viewModel { FncWalsControlViewModel() }
    viewModel { FncRgbControlViewModel() }
    viewModel { TcControlViewModel() }
}