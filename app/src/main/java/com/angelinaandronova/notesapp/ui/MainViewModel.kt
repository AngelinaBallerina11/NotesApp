package com.angelinaandronova.notesapp.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.angelinaandronova.notesapp.domain.*
import com.angelinaandronova.notesapp.model.Note
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val commandProcessor: CommandProcessor
) : ViewModel() {
    private val liveData = MutableLiveData<List<Note>>()
    @Inject lateinit var getNotes: GetNotes
    @Inject lateinit var add: AddNote
    @Inject lateinit var delete: DeleteNote

    fun getNotes(): LiveData<List<Note>> {
        liveData.value = getNotes.execute()
        return liveData
    }

    fun deleteNote(note: Note) {
        commandProcessor.execute(delete.with(note))
    }

    fun undoDelete() {
        val lastCommand = commandProcessor.getLastCommand()
        if (lastCommand is UndoableCommand)
            lastCommand.undo()
    }

    fun saveNewNote(noteText: String) {
        commandProcessor.execute(
            add.with(Note(title = noteText))
        )
    }

}
