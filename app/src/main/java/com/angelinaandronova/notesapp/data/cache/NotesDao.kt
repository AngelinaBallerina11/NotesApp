package com.angelinaandronova.notesapp.data.cache

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.angelinaandronova.notesapp.model.Note

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSingleNote(Note: Note)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotes(notes: List<Note>)

    @Query("SELECT * FROM notes")
    fun getAllNotes(): LiveData<List<Note>>

    @Delete
    fun deleteNote(Note: Note)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateSingleNote(note: Note)

    @Query("SELECT * FROM notes WHERE id = :id")
    fun getNoteById(id: Int): LiveData<Note>
}