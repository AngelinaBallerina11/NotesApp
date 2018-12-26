package com.angelinaandronova.notesapp.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.angelinaandronova.notesapp.R
import com.angelinaandronova.notesapp.model.Note
import kotlinx.android.synthetic.main.note.view.*
import javax.inject.Inject


class NotesAdapter @Inject constructor() : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {
    var notes: List<Note> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.note, parent, false)
        return NoteViewHolder(itemView)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.titleTextView.text = notes[position].title
    }

    inner class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val rootView = view
        val titleTextView: TextView = view.title
    }
}


