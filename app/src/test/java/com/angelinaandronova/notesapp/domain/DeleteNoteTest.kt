package com.angelinaandronova.notesapp.domain

import com.angelinaandronova.notesapp.model.Note
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.only
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DeleteNoteTest {

    @Mock val mockRepository = mock<NotesRepository>()
    private lateinit var deleteNoteCommand: DeleteNote

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        deleteNoteCommand = DeleteNote(mockRepository)
    }

    @Test(expected = KotlinNullPointerException::class)
    fun `add note execute throws exception when note is not set`() {
        deleteNoteCommand.execute()
    }

    @Test
    fun `when note is set execute calls repository once`() {
        val note = Note(1, "abc")
        deleteNoteCommand.with(note)
        deleteNoteCommand.execute()
        Mockito.verify(mockRepository, only()).delete(note)
    }

    @Test
    fun `coroutine cancel job propagates to repository`() {
        deleteNoteCommand.cancelJob()
        Mockito.verify(mockRepository, times(1)).cancelJob()
    }

    @Test
    fun `undo should add the deleted note`() {
        val note = Note(1, "abc")
        deleteNoteCommand.with(note)
        deleteNoteCommand.undo()
        Mockito.verify(mockRepository, only()).addNote(note)
    }
}