package com.example.mynotes

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mynotes.data.toLocalDateTime

class NoteEditorActivity : ComponentActivity() {
    override fun onStart() {
        super.onStart()
        setContent {
            val created = intent.getLongExtra("created", 0).toLocalDateTime()
            NoteEditor(created)
        }
    }
}