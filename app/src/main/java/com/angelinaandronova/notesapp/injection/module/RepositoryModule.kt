package com.angelinaandronova.notesapp.injection.module

import com.angelinaandronova.notesapp.data.DataSourceSwitch
import com.angelinaandronova.notesapp.data.DataSourceSwitchImpl
import com.angelinaandronova.notesapp.data.NotesRepositoryImpl
import com.angelinaandronova.notesapp.domain.NotesRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindRepository(dataRepository: NotesRepositoryImpl): NotesRepository

    @Binds
    abstract fun provideSwitch(switch: DataSourceSwitchImpl): DataSourceSwitch
}