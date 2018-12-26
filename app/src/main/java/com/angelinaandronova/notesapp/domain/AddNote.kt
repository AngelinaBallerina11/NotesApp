package com.angelinaandronova.notesapp.domain

import com.angelinaandronova.notesapp.model.Note
import javax.inject.Inject


class AddNote @Inject constructor(
    val note: Note,
    private val repository: NotesRepository
) : Command {

    override fun execute() {
        repository.addNote(note)
    }
}