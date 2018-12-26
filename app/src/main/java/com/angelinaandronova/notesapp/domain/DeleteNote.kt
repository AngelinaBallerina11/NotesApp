package com.angelinaandronova.notesapp.domain

import com.angelinaandronova.notesapp.model.Note


class DeleteNote(val note: Note): UndoableCommand {
    override fun execute() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun undo() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}