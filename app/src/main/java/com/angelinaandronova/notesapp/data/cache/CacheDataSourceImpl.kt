package com.angelinaandronova.notesapp.data.cache

import android.arch.lifecycle.LiveData
import com.angelinaandronova.notesapp.model.Config
import com.angelinaandronova.notesapp.model.Note
import javax.inject.Inject


class CacheDataSourceImpl @Inject constructor(
    private val db: NoteDatabase
) : CacheDataSource {

    override fun getSingleNote(id: Int): LiveData<Note> {
        return db.notesDao().getNoteById(id)
    }

    override fun getNotes(): LiveData<List<Note>> {
        return db.notesDao().getAllNotes()
    }

    override fun addNote(note: Note) {
        db.notesDao().insertSingleNote(note)
    }

    override fun editNote(note: Note) {
        db.notesDao().updateSingleNote(note)
    }

    override fun delete(note: Note) {
        db.notesDao().deleteNote(note)
    }

    override fun saveAllNotes(notes: List<Note>) {
        db.notesDao().insertNotes(notes)
    }

    override fun setLastCacheTime(lastCache: Long) {
        db.configDao().insertConfig(Config(lastCacheTime = lastCache))
    }

    override fun getLastCacheTime(): Long {
        return db.configDao().getConfig()?.lastCacheTime ?: 0L
    }
}