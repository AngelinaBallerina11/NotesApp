package com.angelinaandronova.notesapp.domain

import java.util.*
import javax.inject.Inject


class CommandProcessor @Inject constructor() {

    private val stack = Stack<Command>()

    fun execute(noteCommand: Command) {
        stack.push(noteCommand)
        noteCommand.execute()
    }

    private fun getLastCommand(): Command? = if (stack.size > 0) stack.pop() else null

    fun undo() {
        val lastCommand = getLastCommand()
        if (lastCommand is UndoableCommand)
            lastCommand.undo()
    }
}