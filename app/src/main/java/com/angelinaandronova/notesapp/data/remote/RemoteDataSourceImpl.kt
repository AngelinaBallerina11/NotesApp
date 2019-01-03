package com.angelinaandronova.notesapp.data.remote

import com.angelinaandronova.notesapp.model.Note
import javax.inject.Inject


class RemoteDataSourceImpl @Inject constructor(
    private val service: NotesService
) : RemoteDataSource {

    override fun fetchNotes(callback: RemoteCallback<List<Note>>) {
        return service.getAllNotes().enqueue {
            onResponse = {
                val body = it.body()
                if (it.isSuccessful && body != null) run {
                    callback.onSuccess(body)
                } else {
                    callback.onFailure(null)
                }
            }

            onFailure = {
                callback.onFailure(null)
            }
        }
    }

    override fun createNote(note: Note, callback: RemoteCallback<Note>) {
        return service.createSingleNote(note).enqueue {
            onResponse = {
                val body = it.body()
                if (it.isSuccessful && body != null) run {
                    callback.onSuccess(body)
                }
            }
            onFailure = { callback.onFailure(null) }
        }
    }

    override fun getNote(id: Int, callback: RemoteCallback<Note>) {
        return service.getSingleNoteById(id).enqueue {
            onResponse = {
                val body = it.body()
                if (it.isSuccessful && body != null) run {
                    callback.onSuccess(body)
                }
            }
            onFailure = { callback.onFailure(null) }
        }
    }

    override fun updateNote(note: Note, callback: RemoteCallback<Note>) {
        return service.updateSingleNote(note.id!!, note).enqueue {
            onResponse = {
                val body = it.body()
                if (it.isSuccessful && body != null) kotlin.run { callback.onSuccess(body) }
            }
            onFailure = { callback.onFailure(null) }
        }
    }

    override fun deleteNote(id: Int, callback: RemoteCallback<Int>) {
        service.deleteSingleNote(id).enqueue {
            onResponse = {
                if (it.code() == 204) {
                    callback.onSuccess(id)
                }
            }
            onFailure = {
                callback.onFailure(null)
            }
        }
    }
}