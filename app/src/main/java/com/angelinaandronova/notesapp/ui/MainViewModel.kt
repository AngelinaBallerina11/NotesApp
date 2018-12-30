package com.angelinaandronova.notesapp.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.angelinaandronova.notesapp.domain.*
import com.angelinaandronova.notesapp.model.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class MainViewModel @Inject constructor(
    private val commandProcessor: CommandProcessor
) : ViewModel(), CoroutineScope {

    var job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    private var notesList: LiveData<List<Note>>? = null
    private var editedNote: LiveData<Note>? = null
    @Inject lateinit var getNotes: GetNotes
    @Inject lateinit var add: AddNote
    @Inject lateinit var delete: DeleteNote
    @Inject lateinit var getSingleNote: GetSingleNote
    @Inject lateinit var editNote: EditNote

    private fun fetchNotes() {
        notesList = getNotes.execute()
    }

    fun getNotes(): LiveData<List<Note>>? {
        if (notesList == null) fetchNotes()
        return notesList
    }

    fun deleteNote(note: Note) = launch {
        commandProcessor.execute(delete.with(note))
    }

    fun undoDelete() = launch {
        commandProcessor.undo()
    }

    fun saveNewNote(noteText: String) = launch {
        commandProcessor.execute(add.with(Note(title = noteText)))
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun getNoteById(noteId: Int) {
        val noteCommand = getSingleNote.with(noteId)
        commandProcessor.execute(noteCommand)
        editedNote = (noteCommand as GetSingleNote).getResult()
    }

    fun getEditedNote(): LiveData<Note>? = editedNote

    fun saveExistingNote(editedText: String) {
        editedNote?.value?.let {
            launch {
                commandProcessor.execute(editNote.with(it.copy(title = editedText)))
            }
        }
    }
}
