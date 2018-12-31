package com.angelinaandronova.notesapp.data.remote

import com.angelinaandronova.notesapp.model.Note


interface RemoteDataSource {
    fun getNotes(callback: GetNotesCallback)
    fun createNote(note: Note, callback: CreateNoteCallback)
    fun getNote(id: Int, callback: GetSingleNoteCallback)
    fun updateNote(note: Note)
    fun deleteNote(id: Int) /* success status 204 */
}