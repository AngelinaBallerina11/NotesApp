package com.angelinaandronova.notesapp.domain


abstract class UndoableCommand : Command() {
    abstract fun undo()
}