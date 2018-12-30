package com.angelinaandronova.notesapp.data.remote

import com.angelinaandronova.notesapp.model.Note
import retrofit2.http.*


interface NotesService {

    companion object {
        const val NOTES_ENDPOINT = "/notes"
    }

    @GET(NOTES_ENDPOINT)
    fun getNotes(): List<Note>

    @POST(NOTES_ENDPOINT)
    fun createNote(@Body note: Note): Note

    @GET("$NOTES_ENDPOINT/{id}")
    fun retrieveNote(@Path("id") id: Long): Note

    @PUT("$NOTES_ENDPOINT/{id}")
    fun updateNote(@Path("id") id: Long?, note: Note)

    @DELETE("$NOTES_ENDPOINT/{id}")
    fun deleteNote(@Path("id") id: Long)
}