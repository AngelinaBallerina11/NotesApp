package com.angelinaandronova.notesapp.data.cache

import android.arch.lifecycle.LiveData
import com.angelinaandronova.notesapp.model.Note


interface CacheDataSource {
    fun getNotes(): LiveData<List<Note>>
    fun addNote(note: Note)
    fun editNote(note: Note)
    fun delete(note: Note)
    fun getSingleNote(id: Int): LiveData<Note>
    fun saveAllNotes(notes: List<Note>)
    fun setLastCacheTime(lastCache: Long)
}