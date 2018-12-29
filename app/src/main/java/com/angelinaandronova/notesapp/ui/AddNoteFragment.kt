package com.angelinaandronova.notesapp.ui

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
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

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
        save_note_btn.setOnClickListener {
            viewModel.saveNewNote(new_note_text.text.toString())
            dialog.dismiss()
        }
    }

    private fun showSoftKeyboard() {
        dialog.window?.setSoftInputMode(SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }
}