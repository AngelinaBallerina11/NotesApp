package com.angelinaandronova.notesapp.domain

import android.arch.lifecycle.LiveData
import com.angelinaandronova.notesapp.model.Note
import javax.inject.Inject


class GetSingleNote @Inject constructor(
    private val repository: NotesRepository
) : Command() {
    private var result: LiveData<Note>? = null

    override fun execute() {
        result = repository.getSingleNote(noteId!!)
    }

    fun getResult(): LiveData<Note>? = result

}