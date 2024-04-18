package com.example.mynotes.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.mynotes.data.DB
import com.example.mynotes.data.Note

@Composable
fun NoteDropdownMenu(note: Note, onDismissRequest: () -> Unit, onNoteRemoved: (Note) -> Unit) {
    Box(modifier = Modifier.size(100.dp)) {
        DropdownMenu(expanded = true, onDismissRequest = onDismissRequest) {
            DropdownMenuItem(text = { Text(text = "Delete")}, onClick = {
                onNoteRemoved(note)
                onDismissRequest()
            })
        }
    }
}