package com.example.mynotes.ui


import android.content.Intent
import android.os.Bundle
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.example.mynotes.NoteEditorActivity
import com.example.mynotes.data.DB
import com.example.mynotes.data.Note
import com.example.mynotes.data.toLocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun MainMenu() {
    val context = LocalContext.current
    var notes by remember { mutableStateOf(DB.getNotes(context)) }

    val addNewNote: () -> Unit = {
        val newNote = Note("New Note")
        notes += newNote
        DB.saveNote(context, newNote)

    }
    val removeNote: (Note) -> Unit = {
        notes -= it
        DB.deleteNote(context, it)
    }
    val editNote: (Note) -> Unit = {
        val currNote = notes.find { note -> it.created == note.created }
        currNote?.text = it.text
        currNote?.title = it.title
        DB.saveNote(context, it)
    }

    Column {
        TopBar(addNewNote)

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(notes) { note ->
                NoteCard(note = note, onNoteRemoved = removeNote, onNoteEdited = editNote)
            }
        }
    }
}

@Composable
fun TopBar(onNewNoteAdded: () -> Unit) {
    TopAppBar(
        title = {},
        modifier = Modifier.height(64.dp),
        colors = topAppBarColors(MaterialTheme.colorScheme.primary),
        actions = {
            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "My Notes",
                    fontSize = 24.sp,
                    style = TextStyle(color = Color.White),
                    modifier = Modifier.padding(top = 8.dp)
                )
                Button(onClick = {
                    onNewNoteAdded()
                }, modifier = Modifier.padding(top = 4.dp)) {
                    Text(text = "+", fontSize = 16.sp)
                }
            }
        }
    )
}

@Composable
fun NoteCard(
    note: Note,
    modifier: Modifier = Modifier,
    onNoteRemoved: (Note) -> Unit,
    onNoteEdited: (Note) -> Unit
) {
    val context = LocalContext.current
    var isDropdownMenuVisible by remember { mutableStateOf(false) }

    // render the dropdown menu
    if (isDropdownMenuVisible) {
        NoteDropdownMenu(note, onDismissRequest = { isDropdownMenuVisible = false}, onNoteRemoved = onNoteRemoved)
    }

    // render the card
    Card(
        modifier = modifier.pointerInput(Unit) { detectTapGestures(
            onTap = {
                val intent = Intent(context, NoteEditorActivity::class.java)
                intent.putExtra("created", note.created)
                startActivity(context, intent, null)
                isDropdownMenuVisible = false
            }, 
            onLongPress = { isDropdownMenuVisible = true }
        )},
    ) {
        Column {
            Text(
                text = note.title,
                modifier = Modifier.padding(16.dp).height(40.dp),
                style = MaterialTheme.typography.headlineSmall,
            )
            Text(
                text = note.created.toLocalDateTime().format(DateTimeFormatter.ofPattern("d MMM uuuu")),
                modifier = Modifier.padding(4.dp),
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}