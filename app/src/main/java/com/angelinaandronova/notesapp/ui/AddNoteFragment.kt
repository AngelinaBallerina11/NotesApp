package com.angelinaandronova.notesapp.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE
import com.angelinaandronova.notesapp.R
import com.angelinaandronova.notesapp.injection.module.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.add_note_dialog.*
import javax.inject.Inject


class AddNoteFragment : DialogFragment() {

    private lateinit var viewModel: MainViewModel
    @Inject lateinit var viewModelFactory: ViewModelFactory
    private var editMode: Boolean = false

    companion object {
        private const val noteIdArgument: String = "note_id_arg"

        fun getInstance(noteId: Int? = null): AddNoteFragment {
            val myFragment = AddNoteFragment()
            noteId?.let {
                val args = Bundle()
                args.putInt(noteIdArgument, it)
                myFragment.arguments = args
            }
            return myFragment
        }
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.add_note_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(MainViewModel::class.java)
        showSoftKeyboard()
        setFabClickListener()
        setNoteTextIfEditMode()
    }

    private fun setNoteTextIfEditMode() {
        val noteId = arguments?.getInt(noteIdArgument)
        noteId?.let {
            editMode = true
            viewModel.getNoteById(noteId)
            viewModel.getEditedNote()?.observe(this, Observer { note ->
                note?.let {
                    new_note_text.setText(it.title)
                }
            })
        }
    }

    private fun setFabClickListener() {
        save_note_btn.setOnClickListener {
            if (editMode) {
                viewModel.saveExistingNote(new_note_text.text.toString())
                dialog.dismiss()
            } else {
                viewModel.saveNewNote(new_note_text.text.toString())
                dialog.dismiss()
            }
        }
    }

    private fun showSoftKeyboard() {
        dialog.window?.setSoftInputMode(SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }
}