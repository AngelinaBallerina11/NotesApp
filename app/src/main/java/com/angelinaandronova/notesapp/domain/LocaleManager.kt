package com.angelinaandronova.notesapp.domain

import android.content.Context
import android.content.res.Configuration
import android.preference.PreferenceManager
import java.util.*


object LocaleManager {

    private const val LANGUAGE_KEY = "language_key"

    fun setLanguage(language: String, context: Context) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        prefs.edit().putString(LANGUAGE_KEY, language).apply()
    }

    fun updateResources(context: Context?) {
        context?.let {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            val locale = Locale(prefs.getString(LANGUAGE_KEY, NotesLocale.ENG.language))
            Locale.setDefault(locale)
            val config = Configuration(context.resources?.configuration)
            config.setLocale(locale)
            context.resources.updateConfiguration(config, context.resources.getDisplayMetrics())
        }
    }
}

enum class NotesLocale(val language: String) {
    ENG("en"), CZE("cs"), RUS("ru")
}