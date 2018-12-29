package com.angelinaandronova.notesapp.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.angelinaandronova.notesapp.domain.*
import com.angelinaandronova.notesapp.model.Note
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val commandProcessor: CommandProcessor,
    private val notesRepository: NotesRepository
) : ViewModel() {
    private val liveData = MutableLiveData<List<Note>>()

    init {
        commandProcessor
            .addToStack(AddNote(Note(100, "new note: test"), notesRepository))
    }

    fun getNotes(): LiveData<List<Note>> {
        liveData.value = GetNotes(notesRepository).execute()
        return liveData
    }

    fun deleteNote(note: Note) {
        commandProcessor
            .addToStack(DeleteNote(note, notesRepository))
    }

    fun undoDelete() {
        val lastCommand = commandProcessor.getLastCommand()
        if (lastCommand is UndoableCommand)
            lastCommand.undo()
    }

    fun saveNewNote(noteText: String) {
        commandProcessor.addToStack(AddNote(
            Note(title = noteText),
            repository = notesRepository
        ))
    }

}
