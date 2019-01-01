package com.angelinaandronova.notesapp.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.*
import com.angelinaandronova.notesapp.R
import com.angelinaandronova.notesapp.domain.NotesLocale.*
import com.angelinaandronova.notesapp.injection.module.ViewModelFactory
import com.angelinaandronova.notesapp.model.Note
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.main_fragment.*
import javax.inject.Inject

class MainFragment : Fragment(), NotesAdapter.NoteCallback {

    companion object {
        private const val DIALOG = "dialog"
        fun newInstance() = MainFragment()
    }

    private lateinit var adapter: NotesAdapter
    private lateinit var viewModel: MainViewModel
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        activity?.menuInflater?.inflate(R.menu.language_menu, menu)
    }

    override fun onStart() {
        super.onStart()
        initViewModel()
        initRecyclerview()
        addSwipeListener()
        add_fab.setOnClickListener { onAddNoteClicked() }
    }

    private fun onAddNoteClicked() {
        AddNoteFragment.getInstance().show(fragmentManager, DIALOG)
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getNotes()?.observe(this, Observer {
            it?.let { list ->
                adapter.run {
                    notes = list as ArrayList<Note>
                    notifyDataSetChanged()
                }
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

    override fun showUndoDeleteMessage() {
        val undoSnackbar = Snackbar.make(
            main_fragment,
            R.string.note_deleted, Snackbar.LENGTH_SHORT
        );
        undoSnackbar.setAction(R.string.undo_string) { viewModel.undoDelete() };
        undoSnackbar.show()
    }

    override fun onNoteClicked(noteId: Int) {
        AddNoteFragment.getInstance(noteId).show(fragmentManager, DIALOG)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.english -> viewModel.switchLanguage(ENG)
            R.id.czech -> viewModel.switchLanguage(CZE)
            R.id.russian -> viewModel.switchLanguage(RUS)
        }
        activity?.startActivity(Intent(activity, MainActivity::class.java))
        activity?.finish()
        return super.onOptionsItemSelected(item)
    }

}


