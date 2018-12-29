package com.angelinaandronova.notesapp.domain

import java.util.*
import javax.inject.Inject


class CommandProcessor @Inject constructor() {

    private val stack = Stack<Command>()

    fun addToStack(noteCommand: Command) {
        stack.push(noteCommand)
        noteCommand.execute()
    }

    fun getLastCommand(): Command? = if (stack.size > 0) stack.pop() else null
}