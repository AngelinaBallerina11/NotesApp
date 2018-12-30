package com.angelinaandronova.notesapp.domain

import android.arch.lifecycle.LiveData
import com.angelinaandronova.notesapp.model.Note
import javax.inject.Inject


class GetNotes @Inject constructor(
    private val repository: NotesRepository
) {
    fun execute(): LiveData<List<Note>> = repository.getNotes()
}