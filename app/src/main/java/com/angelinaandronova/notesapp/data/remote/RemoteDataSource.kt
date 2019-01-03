package com.angelinaandronova.notesapp.data.remote

import com.angelinaandronova.notesapp.model.Note


interface RemoteDataSource {
    fun fetchNotes(callback: RemoteCallback<List<Note>>)
    fun createNote(note: Note, callback: RemoteCallback<Note>)
    fun getNote(id: Int, callback: RemoteCallback<Note>)
    fun updateNote(note: Note, callback: RemoteCallback<Note>)
    fun deleteNote(id: Int, callback: RemoteCallback<Int>) /* success status 204 */
}