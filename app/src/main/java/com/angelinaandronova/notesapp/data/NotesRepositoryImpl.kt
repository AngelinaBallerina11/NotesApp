package com.angelinaandronova.notesapp.data

import android.util.Log
import com.angelinaandronova.notesapp.domain.NotesRepository
import com.angelinaandronova.notesapp.model.Note
import javax.inject.Inject


class NotesRepositoryImpl @Inject constructor() : NotesRepository {

    private val notes = arrayListOf(
        Note(1, "Need to walk my dog"),
        Note(2, "Make salad Olivier"),
        Note(3, "Learn Design Patterns"),
        Note(4, "Reply messages")
    )

    override fun getNotes(): List<Note> = notes

    override fun addNote(note: Note) {
        notes.add(note)
        log()
    }

    override fun editNote(note: Note) {
        if (notes.contains(note)) {
            notes.remove(note)
            notes.add(note)
        } else {
            notes.add(note)
        }
        log()
    }

    override fun delete(note: Note) {
        notes.remove(note)
        log()
    }

    override fun log() {
        Log.i("AAA", notes.size.toString())
    }
}