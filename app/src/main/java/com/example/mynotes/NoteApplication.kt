package com.example.mynotes

import android.app.Application
import com.example.mynotes.data.AppContainer
import com.example.mynotes.data.AppDataContainer

class NoteApplication: Application() {
    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}