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
        const val seedSql = "insert into notes (id, title) values (1, 'cook dinner')"
    }

    @Provides
    @Singleton
    fun provideDatabase(app: Application): NoteDatabase {
        return Room
            .databaseBuilder(app, NoteDatabase::class.java, appDbName)
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    Executors.newSingleThreadScheduledExecutor()
                        .execute { db.execSQL(seedSql)}
                }
            })
            .build()
    }
}