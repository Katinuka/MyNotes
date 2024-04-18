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
import com.example.mynotes.data.Note
import com.example.mynotes.data.NoteViewModel

@Composable
fun NoteDropdownMenu(viewModel: NoteViewModel, created: Long, onDismissRequest: () -> Unit) {
    val context = LocalContext.current
    Box(modifier = Modifier.size(100.dp)) {
        DropdownMenu(expanded = true, onDismissRequest = onDismissRequest) {
            DropdownMenuItem(text = { Text(text = "Delete")}, onClick = {
                onDismissRequest()
                viewModel.removeNote(context = context, created = created)
            })
        }
    }
}