package com.example.mynotes.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDateTime

class NoteViewModel(private val itemsRepository: NoteRepository) : ViewModel() {
    var state = itemsRepository.getAllNotesStream()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = listOf()
        )

    suspend fun setNoteText(created: Long, newText: String) {
        // TODO: remove this delay :)
        delay(2000)
        val currentNote = state.value.find { it.created == created }
        if (currentNote == null) {
            itemsRepository.insertNote(Note(text = newText, created = created))
            return
        }
        currentNote.text = newText
        itemsRepository.updateNote(currentNote)
//        _state.update { currentState ->
//            currentState.toMutableList().apply {
//            val currentNote = find { it.created == created } ?: return
//            currentNote.text = newText
//            DB.saveNote(context, currentNote)
//        } }
    }

    suspend fun setNoteTitle(created: Long, newTitle: String) {
        val currentNote = state.value.find { it.created == created }
        if (currentNote == null) {
            itemsRepository.insertNote(Note(text = "", title = newTitle, created = created))
            return
        }
        currentNote.title = newTitle
        itemsRepository.updateNote(currentNote)
//        _state.update { currentState -> currentState.toMutableList().apply {
//            val currentNote = find { it.created == created } ?: return
//            currentNote.title = newTitle
//            DB.saveNote(context, currentNote)
//        } }
    }

    suspend fun addNote(newText: String = "", newTitle: String = "New Note") {
        itemsRepository.insertNote(Note(text = newText, title = newTitle, created = LocalDateTime.now()))

        /*val newNote = Note(text = newText, title = newTitle)
        DB.saveNote(context = context, note = newNote) // This is a strange line
        // The first addition creates 2 notes for the UI instead of one
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
        }}*/
    }

    suspend fun removeNote(created: Long) {
        val currentNote = state.value.find { it.created == created } ?: return
        itemsRepository.deleteNote(currentNote)
//        val currentNote = _state.value.find { it.created == created } ?: return
//        _state.update { it.toMutableList().apply { remove(currentNote) } }
//        DB.deleteNote(context = context, note = currentNote)
    }

    fun getNote(created: Long) : Note {
//        return itemsRepository.getNoteStream(created)
//            .stateIn(
//                scope = viewModelScope,
//                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
//                initialValue = Note("Error")
//            ).value ?: Note("Error Null")
        return state.value.find { it.created == created } ?: Note("Error")
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}