package com.angelinaandronova.notesapp.data.remote

import com.angelinaandronova.notesapp.model.Note
import retrofit2.http.*


interface NotesService {

    companion object {
        const val NOTES_ENDPOINT = "/notes"
    }

    @GET(NOTES_ENDPOINT)
    fun getAllNotes(): List<Note>

    @POST(NOTES_ENDPOINT)
    fun createSingleNote(@Body note: Note): Note

    @GET("$NOTES_ENDPOINT/{id}")
    fun getSingleNoteById(@Path("id") id: Long): Note

    @PUT("$NOTES_ENDPOINT/{id}")
    fun updateSingleNote(@Path("id") id: Long?, note: Note)

    @DELETE("$NOTES_ENDPOINT/{id}")
    fun deleteSingleNote(@Path("id") id: Long)
}