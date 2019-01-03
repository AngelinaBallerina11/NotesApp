package com.angelinaandronova.notesapp.domain

import com.angelinaandronova.notesapp.model.Note
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class CommandProcessorTest {

    lateinit var processor: CommandProcessor
    private val repoMock = mock<NotesRepository>()

    @Before
    fun setUp() {
        processor = CommandProcessor()
    }

    @Test
    fun `calling execute add one item to the stack`() {
        val command = AddNote(repoMock)
        val note = Note(123, "abc")
        processor.execute(command.with(note))
        Mockito.verify(repoMock, times(1)).addNote(note)
    }

    @Test
    fun `delete command should call delete in repository`() {
        val command = DeleteNote(repoMock)
        val note = Note(1234, "abcd")
        processor.execute(command.with(note))
        Mockito.verify(repoMock, times(1)).delete(note)
    }

    @Test
    fun `undo command should add the same note back`() {
        val command = DeleteNote(repoMock)
        val note = Note(1234, "abcd")
        processor.execute(command.with(note))
        processor.undo()
        Mockito.verify(repoMock, times(1)).addNote(note)
    }
}