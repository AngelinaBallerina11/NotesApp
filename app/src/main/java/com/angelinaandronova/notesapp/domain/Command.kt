package com.angelinaandronova.notesapp.domain

import com.angelinaandronova.notesapp.model.Note


abstract class Command constructor(
    private val repository: NotesRepository
) {
    abstract fun execute()

    fun cancelJob() {
        repository.cancelJob()
    }

    internal var note: Note? = null
    fun with(note: Note): Command = apply {
        this.note = note
    }

    internal var noteId: Int? = null
    fun with(id: Int): Command = apply {
        this.noteId = id
    }
}