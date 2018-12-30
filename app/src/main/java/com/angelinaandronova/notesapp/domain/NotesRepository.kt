package com.angelinaandronova.notesapp.domain

import android.arch.lifecycle.LiveData
import com.angelinaandronova.notesapp.model.Note


interface NotesRepository {
    fun getNotes(): LiveData<List<Note>>
    fun addNote(note: Note)
    fun editNote(note: Note)
    fun delete(note: Note)
}