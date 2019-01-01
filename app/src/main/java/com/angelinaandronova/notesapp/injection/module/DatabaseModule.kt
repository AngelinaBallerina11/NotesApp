package com.angelinaandronova.notesapp.injection.module

import android.app.Application
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import com.angelinaandronova.notesapp.data.cache.NoteDatabase
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executors
import javax.inject.Singleton


@Module
class DatabaseModule {

    companion object {
        const val appDbName = "notes_db"
    }

    @Provides
    @Singleton
    fun provideDatabase(app: Application): NoteDatabase {
        return Room
            .databaseBuilder(app, NoteDatabase::class.java, appDbName)
            .build()
    }
}