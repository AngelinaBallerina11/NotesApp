package com.angelinaandronova.notesapp.domain

import javax.inject.Inject


class AddNote @Inject constructor(
    private val repository: NotesRepository
) : Command() {

    override fun execute() {
        repository.addNote(note)
    }
}