package com.angelinaandronova.notesapp.domain

import com.angelinaandronova.notesapp.model.Note
import javax.inject.Inject


class GetNotes @Inject constructor(
    private val repository: NotesRepository
) {
    fun execute(): List<Note> = repository.getNotes()
}