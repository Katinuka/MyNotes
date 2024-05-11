package com.example.mynotes.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.mynotes.data.NoteViewModel
import kotlinx.coroutines.launch

@Composable
fun NoteDropdownMenu(viewModel: NoteViewModel, created: Long, onDismissRequest: () -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Box(modifier = Modifier.size(100.dp)) {
        DropdownMenu(expanded = true, onDismissRequest = onDismissRequest) {
            DropdownMenuItem(text = { Text(text = "Delete")}, onClick = {
                coroutineScope.launch {
                    runCatching {
                        viewModel.removeNote(created = created)
                        onDismissRequest()
                    }.onSuccess {
                        Toast.makeText(context, "Success", Toast.LENGTH_LONG).show()
                    }.onFailure {
                        Log.e("MainMenu", "Couldn't remove a note: ${it.message}")
                        Toast.makeText(context, "Couldn't remove a note! Report this", Toast.LENGTH_LONG).show()
                    }
                }
            })
        }
    }
}