package com.angelinaandronova.notesapp.domain

import com.angelinaandronova.notesapp.model.Note
import javax.inject.Inject


class EditNote @Inject constructor(
    val note: Note,
    private val repository: NotesRepository
) : Command {
    override fun execute() {
        repository.editNote(note)
    }
}