package com.angelinaandronova.notesapp.domain

import javax.inject.Inject


class CommandProcessor @Inject constructor() {

    private val queue = arrayListOf<Command>()

    fun addToQueue(noteCommand: Command): CommandProcessor = apply { queue.add(noteCommand) }

    fun processCommands(): CommandProcessor = apply {
        queue.forEach { it.execute() }
        queue.clear()
    }
}