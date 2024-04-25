package com.example.mynotes.data

import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(private val noteDAO: NoteDAO): NoteRepository {
    override fun getAllNotesStream(): Flow<List<Note>> {
        return noteDAO.getAllNotes()
    }

    override fun getNoteStream(id: Long): Flow<Note?> {
        return noteDAO.getNote(id)
    }

    override suspend fun insertNote(note: Note) {
        return noteDAO.insert(note)
    }

    override suspend fun deleteNote(note: Note) {
        return noteDAO.delete(note)
    }

    override suspend fun updateNote(note: Note) {
        return noteDAO.update(note)
    }

}