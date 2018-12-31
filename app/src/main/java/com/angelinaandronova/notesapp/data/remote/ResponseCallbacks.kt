package com.angelinaandronova.notesapp.data.remote

import com.angelinaandronova.notesapp.model.Note


interface GetNotesCallback {
    fun onGetNotes(data: List<Note>?)
}

interface CreateNoteCallback {
    fun onCreateNote(note: Note?)
}

interface GetSingleNoteCallback {
    fun onGetSingleNote(note: Note?)
}

interface UpdateNoteCallback {
    fun onUpdateNote(note: Note?)
}