package com.example.mynotes.data

import android.content.Context
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NoteViewModel(context: Context) : ViewModel() {
    private val _state = MutableStateFlow(DB.getNotes(context))
    var state: StateFlow<MutableList<Note>> = _state.asStateFlow()

    fun setNoteText(created: Long, newText: String) {
        val currentNote = _state.value.find { it.created == created } ?: return
        val newTitle = if (currentNote.title != "") currentNote.title else formatTitle(newText)

        _state.update { it.toMutableList().apply {
            currentNote.title = newTitle
            currentNote.text = newText
        } }
    }

    fun setNoteTitle(created: Long, newTitle: String) {
        val currentNote = _state.value.find { it.created == created } ?: return
        _state.update { it.toMutableList().apply { currentNote.text = newTitle } }
    }

    fun addNote(context: Context, newText: String = "", newTitle: String = "ez") {
        // TODO: find out how the state update works
        // The first code block works while
        // the second one doesn't update the state for the UI part of the app
        /*_state.update { it.toMutableList().apply {
            val a = add(Note(text = newText, title = newTitle))
            DB.saveNote(context = context, note = Note(text = newText, title = newTitle))
        } }*/

        _state.update { it.toMutableList().apply {
            val newNote = Note(text = newText, title = newTitle)
            val a = add(newNote)
            DB.saveNote(context = context, note = newNote)
        } }
    }

    fun removeNote(context: Context, created: Long) {
        val currentNote = _state.value.find { it.created == created } ?: return
        _state.update { it.toMutableList().apply { remove(currentNote) } }
        DB.deleteNote(context = context, note = currentNote)
    }

    fun getNote(created: Long) : Note {
        return _state.value.find { it.created == created } ?: Note("Error")
    }

    fun ez() {
        _state.update { it ->
            it.toMutableList().apply { add(Note("ez2")) }
        }
    }
}