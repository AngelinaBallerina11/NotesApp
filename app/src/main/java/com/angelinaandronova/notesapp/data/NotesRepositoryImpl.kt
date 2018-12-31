package com.angelinaandronova.notesapp.data

import android.arch.lifecycle.LiveData
import com.angelinaandronova.notesapp.data.cache.CacheDataSource
import com.angelinaandronova.notesapp.domain.NotesRepository
import com.angelinaandronova.notesapp.model.Note
import javax.inject.Inject


class NotesRepositoryImpl @Inject constructor(
    private val cache: CacheDataSource
) : NotesRepository {

    override fun getSingleNote(id: Int): LiveData<Note> {
        return cache.getSingleNote(id)
    }

    override fun getNotes(): LiveData<List<Note>> {
        return cache.getNotes()
    }

    override fun addNote(note: Note) {
        cache.addNote(note)
    }

    override fun editNote(note: Note) {
        cache.editNote(note)
    }

    override fun delete(note: Note) {
        cache.delete(note)
    }
}