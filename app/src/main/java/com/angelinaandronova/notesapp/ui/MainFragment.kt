package com.angelinaandronova.notesapp.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.angelinaandronova.notesapp.R
import com.angelinaandronova.notesapp.injection.module.ViewModelFactory
import com.angelinaandronova.notesapp.model.Note
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.main_fragment.*
import javax.inject.Inject

class MainFragment : Fragment(), NotesAdapter.OnNoteDeleteCallback {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    lateinit var adapter: NotesAdapter
    @Inject lateinit var viewModelFactory: ViewModelFactory

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()
    }

    override fun onStart() {
        super.onStart()
        initRecyclerview()
        addSwipeListener()
        add_fab.setOnClickListener { onAddNoteClicked() }
    }

    private fun onAddNoteClicked() {
        AddNoteFragment().show(fragmentManager, "dialog")
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getNotes()?.observe(this, Observer {
            it?.let { list ->
                adapter.run { notes = list as ArrayList<Note> }
            }
        })
    }

    private fun addSwipeListener() {
        val swipeHandler = SwipeToDeleteCallback(context!!, adapter)
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerview)
    }

    private fun initRecyclerview() {
        recyclerview.layoutManager = LinearLayoutManager(activity)
        adapter = NotesAdapter(viewModel, this)
        recyclerview.adapter = adapter
        recyclerview.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
    }

    override fun showUndoMessage() {
        val undoSnackbar = Snackbar.make(
            main_fragment,
            R.string.note_deleted, Snackbar.LENGTH_SHORT
        );
        undoSnackbar.setAction(R.string.undo_string) { viewModel.undoDelete() };
        undoSnackbar.show()
    }

}
