package com.example.mynotes.data

import android.content.Context
import com.google.gson.Gson

object DB {
    private val GSON = Gson()

    private val notes = mutableListOf<Note>()

    fun getNotes(context: Context): MutableList<Note> {
        if (notes.isEmpty()) {
            notes.addAll(getAllNotes(context))
        }

        return notes
    }


    fun saveNote(context: Context, note: Note) {
        // update the current collection
        val currentNote = notes.find {it.created == note.created}
        if (currentNote == null) {
            notes.add(note)
        } else {
            currentNote.text = note.text
            currentNote.title = note.title
        }


        val sharedPreferences = context.getSharedPreferences("notes", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("note_${note.created}", GSON.toJson(note))
        editor.apply()
    }

    fun deleteNote(context: Context, note: Note) {
        // update the current collection
        val currentNote = notes.find {it.created == note.created}
        notes.remove(currentNote)

        val sharedPreferences = context.getSharedPreferences("notes", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("note_${note.created}")
        editor.apply()
    }

    private fun getAllNotes(context: Context): List<Note> {
        val sharedPreferences = context.getSharedPreferences("notes", Context.MODE_PRIVATE)
        val notes = mutableListOf<Note>()
        sharedPreferences.all.forEach { (_, value) ->
            val jsonNote = value as String
            val note = GSON.fromJson(jsonNote, Note::class.java)
            notes.add(note)
        }
        return notes
    }
}