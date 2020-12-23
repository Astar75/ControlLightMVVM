package com.astar.osterrig.controllightmvvm.di

import com.astar.osterrig.controllightmvvm.model.datasource.RoomDatabaseImplementation
import com.astar.osterrig.controllightmvvm.model.repository.DevicesRepository
import com.astar.osterrig.controllightmvvm.model.repository.RepositoryImplementation
import com.astar.osterrig.controllightmvvm.view.MainInteractor
import com.astar.osterrig.controllightmvvm.view.MainViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val application = module {
    single<DevicesRepository>(named(NAME_LOCAL)) { RepositoryImplementation(RoomDatabaseImplementation()) }
}

val mainScreen = module {
    factory { MainInteractor(get(named(NAME_LOCAL))) }
    factory { MainViewModel(get()) }
}