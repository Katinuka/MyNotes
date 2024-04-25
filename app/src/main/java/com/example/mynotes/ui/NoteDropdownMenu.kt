package com.example.mynotes.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mynotes.data.NoteViewModel
import kotlinx.coroutines.launch

@Composable
fun NoteDropdownMenu(viewModel: NoteViewModel, created: Long, onDismissRequest: () -> Unit) {
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier.size(100.dp)) {
        DropdownMenu(expanded = true, onDismissRequest = onDismissRequest) {
            DropdownMenuItem(text = { Text(text = "Delete")}, onClick = {
                onDismissRequest()
                coroutineScope.launch {
                    viewModel.removeNote(created = created)
                }
            })
        }
    }
}