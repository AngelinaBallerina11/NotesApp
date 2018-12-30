package com.angelinaandronova.notesapp.data

import android.arch.lifecycle.LiveData
import com.angelinaandronova.notesapp.data.cache.NoteDatabase
import com.angelinaandronova.notesapp.domain.NotesRepository
import com.angelinaandronova.notesapp.model.Note
import javax.inject.Inject


class NotesRepositoryImpl @Inject constructor(
    private val db: NoteDatabase
) : NotesRepository {

    override fun getNotes(): LiveData<List<Note>> {
        return db.notesDao().getAllNotes()
    }

    override fun addNote(note: Note) {
        db.notesDao().insertSingleNote(note)
    }

    override fun editNote(note: Note) {
        // TODO
    }

    override fun delete(note: Note) {
        db.notesDao().deleteNote(note)
    }
}