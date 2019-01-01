package com.angelinaandronova.notesapp.domain

import javax.inject.Inject


class AddNote @Inject constructor(
    private val repository: NotesRepository
) : Command(repository) {

    override fun execute() {
        repository.addNote(note)
    }
}