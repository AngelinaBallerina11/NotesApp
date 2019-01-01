package com.angelinaandronova.notesapp

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.angelinaandronova.notesapp.domain.LocaleManager
import com.angelinaandronova.notesapp.injection.DaggerApplicationComponent
import com.facebook.stetho.Stetho
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import java.util.*
import javax.inject.Inject


class NotesApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> {
        return androidInjector
    }

    override fun onCreate() {
        super.onCreate()
        setupTimber()
        setupStetho()

        DaggerApplicationComponent
            .builder()
            .application(this)
            .build()
            .inject(this)
    }

    private fun setupStetho() {
        Stetho.initializeWithDefaults(this)
    }

    private fun setupTimber() {
        Timber.plant(Timber.DebugTree())
    }

}