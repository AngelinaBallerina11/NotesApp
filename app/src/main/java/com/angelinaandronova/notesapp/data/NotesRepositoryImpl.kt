package com.angelinaandronova.notesapp.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.angelinaandronova.notesapp.data.cache.CacheDataSource
import com.angelinaandronova.notesapp.data.remote.*
import com.angelinaandronova.notesapp.domain.NotesRepository
import com.angelinaandronova.notesapp.model.Note
import kotlinx.coroutines.*
import timber.log.Timber
import java.io.IOException
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
        if (isOnline() && isCacheExpired()) {
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
        }
        return cache.getNotes()
    }

    override fun addNote(note: Note) {
        if (isOnline()) {
            remote.createNote(note, object : CreateNoteCallback {
                override fun onCreateNote(note: Note?) {
                    note?.let { launch { cache.addNote(it) } }
                }
            })
        } else {
            launch { cache.addNote(note) }
        }
    }

    override fun getSingleNote(id: Int): LiveData<Note> {
        if (isOnline()) {
            val liveData: MutableLiveData<Note> = MutableLiveData()
            remote.getNote(id, object : GetSingleNoteCallback {
                override fun onGetSingleNote(note: Note?) {
                    note?.let {
                        liveData.value = it
                    }
                }
            })
            return liveData
        }
        return cache.getSingleNote(id)
    }

    override fun editNote(note: Note) {
        if (isOnline()) {
            remote.updateNote(note, object : UpdateNoteCallback {
                override fun onUpdateNote(note: Note?) {
                    note?.let { launch { cache.editNote(note) } }
                }
            })
        } else {
            cache.editNote(note)
        }
    }

    override fun delete(note: Note) {
        if (isOnline()) {
            remote.deleteNote(note.id!!, object : DeleteNoteCallback {
                override fun onDeleteNote(noteId: Int?) {
                    noteId?.let { launch { cache.delete(note) } }
                }
            })
        } else {
            cache.delete(note)
        }
    }

    private fun isCacheExpired(): Boolean {
        var delta = 0L
        runBlocking(Dispatchers.IO) {
            val currentTime = System.currentTimeMillis()
            val lastCacheTime = async { cache.getLastCacheTime() }
            delta = currentTime - lastCacheTime.await()
        }
        Timber.d("delta: $delta")
        return delta > expirationInterval
    }

    private fun isOnline(): Boolean {
        val runtime = Runtime.getRuntime()
        try {
            val ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8")
            val exitValue = ipProcess.waitFor()
            return exitValue == 0
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        return false
    }

    override fun cancelJob() {
        job.cancel()
    }

}