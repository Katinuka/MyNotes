package com.example.mynotes.data

import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getAllNotesStream(): Flow<List<Note>>

    fun getNoteStream(id: Long): Flow<Note?>

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)

    suspend fun updateNote(note: Note)
}