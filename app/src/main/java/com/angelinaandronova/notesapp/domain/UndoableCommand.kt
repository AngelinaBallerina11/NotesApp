package com.angelinaandronova.notesapp.domain


interface UndoableCommand : Command {
    fun undo()
}