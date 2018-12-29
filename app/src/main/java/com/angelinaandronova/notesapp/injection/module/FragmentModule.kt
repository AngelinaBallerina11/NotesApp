package com.angelinaandronova.notesapp.injection.module

import com.angelinaandronova.notesapp.ui.MainFragment
import com.angelinaandronova.notesapp.ui.NotesAdapter
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): MainFragment

    @Binds
    abstract fun provideOnDeleteCallback(mainFragment: MainFragment): NotesAdapter.OnNoteDeleteCallback
}