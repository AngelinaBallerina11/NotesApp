package com.angelinaandronova.notesapp.data.cache

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.angelinaandronova.notesapp.model.Config
import com.angelinaandronova.notesapp.model.Note


@Database(
    entities = [Note::class, Config::class],
    version = 1,
    exportSchema = false
)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao
    abstract fun configDao(): ConfigDao
}