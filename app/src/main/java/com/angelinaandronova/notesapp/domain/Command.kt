package com.angelinaandronova.notesapp.domain

import com.angelinaandronova.notesapp.model.Note


abstract class Command {
    abstract fun execute()

    internal lateinit var note: Note
    fun with(note: Note): Command = apply {
        this.note = note
    }

    internal var noteId: Int? = null
    fun with(id: Int): Command = apply {
        this.noteId = id
    }
}