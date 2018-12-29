package com.angelinaandronova.notesapp.domain

import com.angelinaandronova.notesapp.model.Note


interface NotesRepository {
    fun getNotes(): List<Note>
    fun addNote(note: Note)
    fun editNote(note: Note)
    fun delete(note: Note)
    fun log()
}