package com.example.mynotes

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mynotes.data.toLocalDateTime
import com.example.mynotes.ui.NoteEditor

class NoteEditorActivity : ComponentActivity() {
    override fun onStart() {
        super.onStart()
        setContent {
            val created = intent.getLongExtra("created", 0).toLocalDateTime()
            NoteEditor(created)
        }
        Log.i("NoteEditorActivity", "onStart()")
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        Log.i("NoteEditorActivity", "onCreate()")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("NoteEditorActivity", "onRestart()")
    }

    override fun onPause() {
        super.onPause()
        Log.i("NoteEditorActivity", "onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.i("NoteEditorActivity", "onStop()")
    }

    override fun onResume() {
        super.onResume()
        Log.i("NoteEditorActivity", "onResume()")
    }
}