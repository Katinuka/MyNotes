package com.example.mynotes


import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.example.mynotes.data.DB
import com.example.mynotes.data.Note
import com.example.mynotes.data.toLocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun NotesApp() {
    val context = LocalContext.current
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(DB.getAllNotes(context)) { note ->
            NoteCard(note = note)
        }
    }
}

@Composable
fun NoteCard(note: Note, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Card(
        modifier = modifier,
        onClick = {
            val intent = Intent(context, NoteEditorActivity::class.java)
            intent.putExtra("created", note.created)
            startActivity(context, intent, null)
        }
    ) {
        Column {
            Text(
                text = note.title,
                // TODO: avoid setting height there, I suppose
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