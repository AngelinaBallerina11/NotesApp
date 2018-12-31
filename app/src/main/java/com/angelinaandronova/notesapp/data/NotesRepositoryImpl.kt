package com.angelinaandronova.notesapp.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.angelinaandronova.notesapp.data.cache.CacheDataSource
import com.angelinaandronova.notesapp.data.remote.CreateNoteCallback
import com.angelinaandronova.notesapp.data.remote.GetNotesCallback
import com.angelinaandronova.notesapp.data.remote.GetSingleNoteCallback
import com.angelinaandronova.notesapp.data.remote.RemoteDataSource
import com.angelinaandronova.notesapp.domain.NotesRepository
import com.angelinaandronova.notesapp.model.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


class NotesRepositoryImpl @Inject constructor(
    private val cache: CacheDataSource,
    private val remote: RemoteDataSource
) : NotesRepository, CoroutineScope {

    private val expirationInterval = 60 * 10 * 1000L /* 10 mins */
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    override fun getNotes(): LiveData<List<Note>> {
        remote.getNotes(object : GetNotesCallback {
            override fun onGetNotes(data: List<Note>?) {
                data?.let {
                    launch {
                        cache.saveAllNotes(it)
                        cache.setLastCacheTime(System.currentTimeMillis())
                    }
                }
            }
        })
        return cache.getNotes()
    }

    override fun addNote(note: Note) {
        remote.createNote(note, object : CreateNoteCallback {
            override fun onCreateNote(note: Note?) {
                note?.let { launch { cache.addNote(it) } }
            }
        })
    }

    override fun getSingleNote(id: Int): LiveData<Note> {
        val liveData: MutableLiveData<Note> = MutableLiveData()
        remote.getNote(id, object : GetSingleNoteCallback {
            override fun onGetSingleNote(note: Note?) {
                note?.let {
                    liveData.value = it
                }
            }
        })
        return cache.getSingleNote(id)
    }

    override fun editNote(note: Note) {
        cache.editNote(note)
    }

    override fun delete(note: Note) {
        cache.delete(note)
    }

    private fun isCacheExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        return currentTime - cache.getLastCacheTime() > expirationInterval
    }

}