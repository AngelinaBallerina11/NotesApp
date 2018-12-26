package com.angelinaandronova.notesapp.injection

import android.app.Application
import com.angelinaandronova.notesapp.NotesApplication
import com.angelinaandronova.notesapp.injection.module.ApplicationModule
import com.angelinaandronova.notesapp.injection.module.FragmentModule
import com.angelinaandronova.notesapp.injection.module.PresentationModule
import com.angelinaandronova.notesapp.injection.module.ActivityModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ApplicationModule::class,
        ActivityModule::class,
        FragmentModule::class,
        PresentationModule::class]
)
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    fun inject(app: NotesApplication)

}