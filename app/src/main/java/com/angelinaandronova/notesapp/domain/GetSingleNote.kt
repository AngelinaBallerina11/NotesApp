package com.angelinaandronova.notesapp.domain

import android.arch.lifecycle.LiveData
import com.angelinaandronova.notesapp.model.Note
import javax.inject.Inject


class GetSingleNote @Inject constructor(
    private val repository: NotesRepository
) : CommandWithResult<LiveData<Note>>(repository) {

    override fun fetchValues(): LiveData<Note> {
        return repository.getSingleNote(noteId!!)
    }

}