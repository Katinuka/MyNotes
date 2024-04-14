package com.example.mynotes.data

import android.content.Context
import com.google.gson.Gson

object DB {
    private val GSON = Gson()
    fun saveNote(context: Context, note: Note) {
        val sharedPreferences = context.getSharedPreferences("notes", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("note_${note.created}", GSON.toJson(note))
        editor.apply()
    }

    fun getAllNotes(context: Context): List<Note> {
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