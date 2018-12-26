package com.angelinaandronova.notesapp.data

import com.angelinaandronova.notesapp.domain.NotesRepository
import com.angelinaandronova.notesapp.model.Note
import javax.inject.Inject


class NotesRepositoryImpl @Inject constructor() : NotesRepository {

    private val notes = arrayListOf<Note>(
        Note(1, "Need to walk my dog"),
        Note(2, "Make salad Olivier"),
        Note(3, "Learn Design Patterns"),
        Note(4, "Reply messages")
    )

    override fun getNotes(): List<Note> = notes

    override fun addNote(note: Note) {
        notes.add(note)
    }

    override fun editNote(note: Note) {
        if (notes.contains(note)) {
            notes.remove(note)
            notes.add(note)
        } else {
            notes.add(note)
        }
    }

    override fun delete(note: Note) {
        notes.remove(note)
    }
}