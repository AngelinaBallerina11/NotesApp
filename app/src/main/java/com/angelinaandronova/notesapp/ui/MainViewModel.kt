package com.angelinaandronova.notesapp.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.angelinaandronova.notesapp.domain.CommandProcessor
import com.angelinaandronova.notesapp.domain.GetNotes
import com.angelinaandronova.notesapp.domain.NotesRepository
import com.angelinaandronova.notesapp.model.Note
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val commandProcessor: CommandProcessor,
    private val notesRepository: NotesRepository
) : ViewModel() {
    private val liveData = MutableLiveData<List<Note>>()

    fun getNotes(): LiveData<List<Note>> {
        liveData.value = GetNotes(notesRepository).execute()
        return liveData
    }

}
