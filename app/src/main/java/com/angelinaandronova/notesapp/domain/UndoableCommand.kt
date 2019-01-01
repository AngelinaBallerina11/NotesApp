package com.angelinaandronova.notesapp.domain


abstract class UndoableCommand constructor(
    private val repository: NotesRepository
) : Command(repository) {
    abstract fun undo()
}