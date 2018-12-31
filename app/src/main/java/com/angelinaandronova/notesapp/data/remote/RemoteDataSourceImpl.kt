package com.angelinaandronova.notesapp.data.remote

import com.angelinaandronova.notesapp.model.Note
import javax.inject.Inject


class RemoteDataSourceImpl @Inject constructor(
    private val service: NotesService
) : RemoteDataSource {

    override fun getNotes(callback: GetNotesCallback) {
        return service.getAllNotes().enqueue {
            onResponse = {
                if (it.isSuccessful) run {
                    callback.onGetNotes(it.body())
                }
            }

            onFailure = {
                callback.onGetNotes(null)
            }
        }
    }

    override fun createNote(note: Note, callback: CreateNoteCallback) {
        return service.createSingleNote(note).enqueue {
            onResponse = {
                if (it.isSuccessful) run {
                    callback.onCreateNote(it.body())
                }
            }
            onFailure = { callback.onCreateNote(null) }
        }
    }

    override fun getNote(id: Int, callback: GetSingleNoteCallback) {
        return service.getSingleNoteById(id).enqueue {
            onResponse = {
                if (it.isSuccessful) run {
                    callback.onGetSingleNote(it.body())
                }
            }
            onFailure = { callback.onGetSingleNote(null) }
        }
    }

    override fun updateNote(note: Note) {
        return service.updateSingleNote(note.id!!, note).enqueue {
            onResponse = {}
            onFailure = {}
        }
    }

    override fun deleteNote(id: Int) {
        service.deleteSingleNote(id).enqueue {
            onResponse = {}
            onFailure = {}
        }
    }
}