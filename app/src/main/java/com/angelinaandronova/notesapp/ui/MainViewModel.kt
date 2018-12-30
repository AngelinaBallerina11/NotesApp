package com.angelinaandronova.notesapp.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.angelinaandronova.notesapp.domain.AddNote
import com.angelinaandronova.notesapp.domain.CommandProcessor
import com.angelinaandronova.notesapp.domain.DeleteNote
import com.angelinaandronova.notesapp.domain.GetNotes
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

    private var liveData: LiveData<List<Note>>? = null
    @Inject lateinit var getNotes: GetNotes
    @Inject lateinit var add: AddNote
    @Inject lateinit var delete: DeleteNote

    private fun fetchNotes() {
        liveData = getNotes.execute()
    }

    fun getNotes(): LiveData<List<Note>>? {
        if (liveData == null) fetchNotes()
        return liveData
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
}
