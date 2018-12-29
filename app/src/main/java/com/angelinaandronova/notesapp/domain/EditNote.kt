package com.angelinaandronova.notesapp.domain

import javax.inject.Inject


class EditNote @Inject constructor(
    private val repository: NotesRepository
) : Command() {

    override fun execute() {
        repository.editNote(note)
    }
}