package com.example.mynotes.data

import android.content.Context

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val noteRepository: NoteRepository
}

/**
 * [AppContainer] implementation that provides instance of [NoteDatabase]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [noteRepository]
     */
    override val noteRepository: NoteRepository by lazy {
        NoteRepositoryImpl(NoteDatabase.getDatabase(context).noteDao())
    }
}