package com.angelinaandronova.notesapp.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.angelinaandronova.notesapp.R
import com.angelinaandronova.notesapp.model.Note
import kotlinx.android.synthetic.main.note.view.*


class NotesAdapter(
    private val viewModel: MainViewModel,
    private val callback: OnNoteDeleteCallback
) : RecyclerView.Adapter<NotesAdapter.VH>(),
    SwipeToDeleteCallback.ItemTouchHelperAdapter {

    var notes: ArrayList<Note> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.note, parent, false)
        return VH(itemView)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.titleTextView.text = notes[position].title
    }

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val rootView = view
        val titleTextView: TextView = view.title
    }

    interface OnNoteDeleteCallback {
        fun showUndoMessage()
    }

    fun addItem(note: Note) {
        notes.add(note)
        notifyItemInserted(notes.size)
    }

    private fun removeAt(position: Int) {
        viewModel.deleteNote(notes[position])
        //notes.removeAt(position)
        notifyItemRemoved(position)
        callback.showUndoMessage()
    }

    override fun onItemDismiss(position: Int) {
        removeAt(position)
    }
}


