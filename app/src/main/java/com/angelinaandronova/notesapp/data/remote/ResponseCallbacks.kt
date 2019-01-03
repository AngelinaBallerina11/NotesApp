package com.angelinaandronova.notesapp.data.remote

import com.angelinaandronova.notesapp.model.Note


interface RemoteCallback<T> {
    fun onSuccess(data: T)
    fun onFailure(throwable: Throwable?)
}