package com.angelinaandronova.notesapp.domain

import com.angelinaandronova.notesapp.model.Note


interface Command {
    fun execute()
}