package com.angelinaandronova.notesapp.data.remote

import com.angelinaandronova.notesapp.model.Note
import retrofit2.Call
import retrofit2.http.*


interface NotesService {

    companion object {
        const val NOTES_ENDPOINT = "/notes"
    }

    @GET(NOTES_ENDPOINT)
    fun getAllNotes(): Call<List<Note>>

    @POST(NOTES_ENDPOINT)
    fun createSingleNote(@Body note: Note): Call<Note>

    @GET("$NOTES_ENDPOINT/{id}")
    fun getSingleNoteById(@Path("id") id: Int): Call<Note>

    @PUT("$NOTES_ENDPOINT/{id}")
    fun updateSingleNote(@Path("id") id: Int, @Body note: Note): Call<Note>

    @DELETE("$NOTES_ENDPOINT/{id}")
    fun deleteSingleNote(@Path("id") id: Int): Call<Unit>
}