package com.angelinaandronova.notesapp.domain

import android.content.Context
import android.content.res.Configuration
import java.util.*


object LocaleManager {

    fun setNewLocale(c: Context, language: String) {
        persistLanguage(c, language);
        updateResources(c, language);
    }

    private fun persistLanguage(context: Context, language: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    fun updateResources(context: Context, language: String) {
        val locale = Locale(language);
        Locale.setDefault(locale);

        val res = context.resources;
        val config = Configuration(res.configuration);
        config.locale = locale;
        res.updateConfiguration(config, res.displayMetrics);
    }
}

enum class NotesLocale(language: String) {
    ENG("en"), CZE("cz"), RUS("ru")
}