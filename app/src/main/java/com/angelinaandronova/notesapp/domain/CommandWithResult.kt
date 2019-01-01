package com.angelinaandronova.notesapp.domain

import javax.inject.Inject


abstract class CommandWithResult<T>(
    private val repository: NotesRepository
) : Command(repository) {

    override fun execute() {
        result = fetchValues()
    }

    var result: T? = null

    abstract fun fetchValues(): T

}