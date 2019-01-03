package com.angelinaandronova.notesapp.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.angelinaandronova.notesapp.data.cache.CacheDataSource
import com.angelinaandronova.notesapp.data.remote.RemoteCallback
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
    private val remote: RemoteDataSource,
    private val switch: DataSourceSwitch
) : NotesRepository, CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    override fun getNotes(): LiveData<List<Note>> {
        if (switch.isOnline() && switch.isCacheExpired()) {
            remote.fetchNotes(object : RemoteCallback<List<Note>> {
                override fun onSuccess(data: List<Note>) {
                    launch {
                        cache.saveAllNotes(data)
                        cache.setLastCacheTime(System.currentTimeMillis())
                    }
                }

                override fun onFailure(throwable: Throwable?) {
                    //TODO("not implemented")
                }
            })
        }
        return cache.getNotes()
    }

    override fun addNote(note: Note) {
        if (switch.isOnline()) {
            remote.createNote(note, object : RemoteCallback<Note> {
                override fun onSuccess(data: Note) {
                    launch { cache.addNote(data) }
                }

                override fun onFailure(throwable: Throwable?) {
                    //TODO("not implemented")
                }
            })
        } else {
            launch { cache.addNote(note) }
        }
    }

    override fun getSingleNote(id: Int): LiveData<Note> {
        if (switch.isOnline()) {
            val liveData: MutableLiveData<Note> = MutableLiveData()
            remote.getNote(id, object : RemoteCallback<Note> {
                override fun onSuccess(note: Note) {
                    liveData.value = note
                }

                override fun onFailure(throwable: Throwable?) {
                    //TODO("not implemented")
                }
            })
            return liveData
        }
        return cache.getSingleNote(id)
    }

    override fun editNote(note: Note) {
        if (switch.isOnline()) {
            remote.updateNote(note, object : RemoteCallback<Note> {
                override fun onSuccess(note: Note) {
                    launch { cache.editNote(note) }
                }

                override fun onFailure(throwable: Throwable?) {
                    // todo
                }
            })
        } else {
            cache.editNote(note)
        }
    }

    override fun delete(note: Note) {
        if (switch.isOnline()) {
            remote.deleteNote(note.id!!, object : RemoteCallback<Int> {
                override fun onSuccess(noteId: Int) {
                    launch { cache.delete(note) }
                }

                override fun onFailure(throwable: Throwable?) {
                    //todo
                }
            })
        } else {
            cache.delete(note)
        }
    }

    override fun cancelJob() {
        job.cancel()
    }

}