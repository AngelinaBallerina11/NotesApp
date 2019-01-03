package com.angelinaandronova.notesapp.data.remote

import com.angelinaandronova.notesapp.model.Note
import com.nhaarman.mockito_kotlin.*
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito.verify
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RemoteDataSourceImplTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var service: NotesService
    private lateinit var dataSource: RemoteDataSource
    private var callbackList: RemoteCallback<List<Note>> = mock()
    private var callbackSingleNote: RemoteCallback<Note> = mock()
    private var callbackId: RemoteCallback<Int> = mock()

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NotesService::class.java)
        dataSource = RemoteDataSourceImpl(service)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    /*  test pass when executed one by one
    *   when executed in batch some of them fail
    * */

    @Test
    fun fetchNotesSuccess() {
        enqueueResponse("all-notes.json")
        dataSource.fetchNotes(callbackList)
        val request = mockWebServer.takeRequest()
        assertThat(request.path, `is`("/notes"))
        verify(callbackList, atLeastOnce()).onSuccess(any())
    }

    @Test
    fun fetchNotesFailure() {
        mockWebServer.enqueue(MockResponse().setResponseCode(500).setBody("[]"))
        dataSource.fetchNotes(callbackList)
        val request = mockWebServer.takeRequest()
        assertThat(request.path, `is`("/notes"))
        verify(callbackList, times(1)).onFailure(null)
    }

    @Test
    fun createNoteSuccess() {
        enqueueResponse("get-note.json")
        val note = Note(10, "ABC")
        dataSource.createNote(note, callbackSingleNote)
        val request = mockWebServer.takeRequest()
        assertThat(request.path, `is`("/notes"))
        verify(callbackSingleNote, times(1)).onSuccess(any())
    }

    @Test
    fun getNote() {
        enqueueResponse("get-note.json")
        val note = Note(30, "Buy cheese")
        dataSource.getNote(10, callbackSingleNote)
        val request = mockWebServer.takeRequest()
        assertThat(request.path, `is`("/notes/10"))
        verify(callbackSingleNote, times(1)).onSuccess(any())
    }

    @Test
    fun updateNote() {
        enqueueResponse("get-note.json")
        val note = Note(10, "ABC")
        dataSource.updateNote(note, callbackSingleNote)
        val request = mockWebServer.takeRequest()
        assertThat(request.path, `is`("/notes/10"))
        verify(callbackSingleNote, times(1)).onSuccess(any())
    }

    @Test
    fun deleteNote() {
        mockWebServer.enqueue(MockResponse().setResponseCode(204))
        val note = Note(10, "ABC")
        dataSource.deleteNote(note.id!!, callbackId)
        val request = mockWebServer.takeRequest()
        assertThat(request.path, `is`("/notes/10"))
        verify(callbackId, times(0)).onSuccess(any())
    }

    private fun enqueueResponse(
        fileName: String,
        headers: Map<String, String> = emptyMap()
    ) {
        val inputStream = javaClass.classLoader
            .getResourceAsStream("api-response/$fileName")
        val source = Okio.buffer(Okio.source(inputStream))
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(
            mockResponse.setBody(source.readString(Charsets.UTF_8))
        )
    }
}