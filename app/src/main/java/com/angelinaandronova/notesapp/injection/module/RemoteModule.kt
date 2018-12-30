package com.angelinaandronova.notesapp.injection.module

import com.angelinaandronova.notesapp.BuildConfig
import com.angelinaandronova.notesapp.data.remote.NotesService
import com.angelinaandronova.notesapp.data.remote.NotesServiceFactory
import dagger.Module
import dagger.Provides


@Module
abstract class RemoteModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideNotesService(): NotesService {
            return NotesServiceFactory.makeNotesService(BuildConfig.DEBUG)
        }
    }

    /* @Binds
     abstract fun bindNotesRemote(notesRemote: NotesRemoteImpl): NotesRemote*/
}