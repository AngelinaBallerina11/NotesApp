package com.angelinaandronova.notesapp.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.angelinaandronova.notesapp.domain.NotesRepository
import com.angelinaandronova.notesapp.model.Note
import javax.inject.Inject


class DummyNotesRepositoryImpl @Inject constructor() : NotesRepository {

    override fun getSingleNote(id: Int): LiveData<Note> {
        val note = notes.first { it.id == id }
        val livedata: MutableLiveData<Note> = MutableLiveData()
        livedata.postValue(note)
        return livedata
    }


    companion object {
        val notes = arrayListOf(
            Note(1, "Need to walk my dog"),
            Note(2, "Make salad Olivier"),
            Note(3, "Learn Design Patterns"),
            Note(4, "Reply messages")
        )
    }

    override fun getNotes(): LiveData<List<Note>> = MutableLiveData()

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