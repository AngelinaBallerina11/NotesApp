package com.angelinaandronova.notesapp.injection

import android.app.Application
import com.angelinaandronova.notesapp.NotesApplication
import com.angelinaandronova.notesapp.injection.module.*
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
        PresentationModule::class,
        RepositoryModule::class,
        DatabaseModule::class,
        CacheModule::class,
        RemoteModule::class]
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