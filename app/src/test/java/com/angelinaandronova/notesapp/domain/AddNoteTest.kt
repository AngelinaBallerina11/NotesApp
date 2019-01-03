package com.angelinaandronova.notesapp.domain

import com.angelinaandronova.notesapp.model.Note
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.only
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class AddNoteTest {

    @Mock val mockRepository = mock<NotesRepository>()
    private lateinit var addNoteCommand: AddNote

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        addNoteCommand = AddNote(mockRepository)
    }

    @Test(expected = KotlinNullPointerException::class)
    fun `add note execute throws exception when note is not set`() {
        addNoteCommand.execute()
    }

    @Test
    fun `when note is set execute calls repository once`() {
        val note = Note(1, "abc")
        addNoteCommand.with(note)
        addNoteCommand.execute()
        Mockito.verify(mockRepository, only()).addNote(note)
    }

    @Test
    fun `coroutine cancel job propagates to repository`() {
        addNoteCommand.cancelJob()
        Mockito.verify(mockRepository, only()).cancelJob()
    }
}