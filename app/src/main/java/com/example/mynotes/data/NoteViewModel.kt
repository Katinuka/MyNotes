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
    var state = _state.asStateFlow()

    fun setNoteText(context: Context, created: Long, newText: String) {
        _state.update { currentState ->
            currentState.toMutableList().apply {
            val currentNote = find { it.created == created } ?: return
            currentNote.text = newText
            DB.saveNote(context, currentNote)
        } }
    }

    fun setNoteTitle(context: Context, created: Long, newTitle: String) {
        _state.update { currentState -> currentState.toMutableList().apply {
            val currentNote = find { it.created == created } ?: return
            currentNote.title = newTitle
            DB.saveNote(context, currentNote)
        } }
    }

    fun addNote(context: Context, newText: String = "", newTitle: String = "New Note") {
        val newNote = Note(text = newText, title = newTitle)
        DB.saveNote(context = context, note = newNote) // This is a strange line
        // The first adding creates 2 notes for the UI instead of one
        // However, if it's below the `_state.update` line, the UI won't be updated
        // In general, the whole thing is wierd:

        //        _state.update {it.toMutableList().apply {
        //            add(Note("test"))
        //        }}    will work and update the UI


        //        _state.update {it.toMutableList().apply {
        //            val newNote = Note("test")
        //            add(newNote)
        //        }}    but this will NOT


        _state.update {currentState -> currentState.toMutableList().apply {
            add(newNote)
        }}
    }

    fun removeNote(context: Context, created: Long) {
        val currentNote = _state.value.find { it.created == created } ?: return
        _state.update { it.toMutableList().apply { remove(currentNote) } }
        DB.deleteNote(context = context, note = currentNote)
    }

    fun getNote(created: Long) : Note {
        return _state.value.find { it.created == created } ?: Note("Error")
    }
}