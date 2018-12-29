package com.angelinaandronova.notesapp.injection.module

import com.angelinaandronova.notesapp.ui.AddNoteFragment
import com.angelinaandronova.notesapp.ui.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): MainFragment

    @ContributesAndroidInjector
    abstract fun contributeAddNoteFragment(): AddNoteFragment
}