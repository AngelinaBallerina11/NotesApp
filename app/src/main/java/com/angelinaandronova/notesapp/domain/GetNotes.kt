package com.angelinaandronova.notesapp.domain

import android.arch.lifecycle.LiveData
import com.angelinaandronova.notesapp.model.Note
import javax.inject.Inject


class GetNotes @Inject constructor(
    private val repository: NotesRepository
) : CommandWithResult<LiveData<List<Note>>>(repository) {

    override fun fetchValues(): LiveData<List<Note>> {
        return repository.getNotes()
    }
}