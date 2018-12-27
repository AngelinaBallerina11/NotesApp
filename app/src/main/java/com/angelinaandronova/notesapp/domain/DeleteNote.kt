package com.angelinaandronova.notesapp.domain

import com.angelinaandronova.notesapp.model.Note
import javax.inject.Inject


class DeleteNote @Inject constructor(
    val note: Note,
    private val repository: NotesRepository
) : UndoableCommand {
    override fun execute() {
        repository.delete(note)
    }

    override fun undo() {
        repository.addNote(note)
    }

}