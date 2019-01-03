package com.angelinaandronova.notesapp.data

import android.util.Log
import com.angelinaandronova.notesapp.data.cache.CacheDataSource
import com.angelinaandronova.notesapp.data.remote.RemoteCallback
import com.angelinaandronova.notesapp.data.remote.RemoteDataSource
import com.angelinaandronova.notesapp.model.Note
import com.nhaarman.mockito_kotlin.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class NotesRepositoryImplTest {

    private var cache: CacheDataSource = mock()
    private var remote: RemoteDataSource = mock()
    private var switch: DataSourceSwitch = mock()
    private lateinit var repositoryImpl: NotesRepositoryImpl

    @Before
    fun setUp() {
        repositoryImpl = NotesRepositoryImpl(cache, remote, switch)
    }

    @Test
    fun `when online and cache expired should fetch from remote`() {
        Mockito.`when`(switch.isOnline()).thenReturn(true)
        Mockito.`when`(switch.isCacheExpired()).thenReturn(true)
        repositoryImpl.getNotes()
        Mockito.verify(remote, times(1)).fetchNotes(any())
    }

    @Test
    fun `when offline and cache expired remote should not be called, data should come from db`() {
        Mockito.`when`(switch.isOnline()).thenReturn(false)
        Mockito.`when`(switch.isCacheExpired()).thenReturn(true)
        repositoryImpl.getNotes()
        Mockito.verify(remote, never()).fetchNotes(any())
        Mockito.verify(cache, atLeastOnce()).getNotes()
    }

    @Test
    fun `when online and cache not expired remote should not be called, data should come from db`() {
        Mockito.`when`(switch.isOnline()).thenReturn(true)
        Mockito.`when`(switch.isCacheExpired()).thenReturn(false)
        repositoryImpl.getNotes()
        Mockito.verify(remote, never()).fetchNotes(any())
        Mockito.verify(cache, atLeastOnce()).getNotes()
    }

}