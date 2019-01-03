package com.angelinaandronova.notesapp.domain

import javax.inject.Inject


class DeleteNote @Inject constructor(
    private val repository: NotesRepository
) : UndoableCommand(repository) {

    override fun execute() {
        repository.delete(note!!)
    }

    override fun undo() {
        repository.addNote(note!!)
    }

}