package com.example.mynotes.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mynotes.data.NoteViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NoteEditor(
    viewModel: NoteViewModel,
    navController: NavHostController,
    created: Long
) {
    val context = LocalContext.current
    val currentNote by remember { mutableStateOf(viewModel.getNote(created = created)) }
    var currentNoteText by remember { mutableStateOf(currentNote.text) }
    var currentNoteTitle by remember { mutableStateOf(currentNote.title) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Button(onClick = {
                currentNote.title = currentNoteTitle
                currentNote.text = currentNoteText
                coroutineScope.launch {
                    runCatching {
                        viewModel.setNoteText(created = created, newText = currentNoteText)
                        viewModel.setNoteTitle(created = created, newTitle = currentNoteTitle)
                    }.onSuccess {
                        Toast.makeText(context, "Saved successfully!", Toast.LENGTH_SHORT).show()
                    }.onFailure {
                        Log.e("NoteEditor", "Couldn't save the note: ${it.message}")
                        Toast.makeText(context, "Couldn't save the note! Report this", Toast.LENGTH_LONG).show()
                    }
                }
                keyboardController?.hide()
            }, modifier = Modifier.padding(top = 8.dp, end = 8.dp)) {
                Text("Save")
            }

            TextField(
                value = currentNoteTitle,
                onValueChange = { newTitle -> currentNoteTitle = newTitle },
                modifier = Modifier.padding(end = 8.dp),
                textStyle = TextStyle(textAlign = TextAlign.Start, fontSize = 22.sp),
                singleLine = true,
                placeholder = { Text("Enter your note title here...") }
            )
        }


        TextField(
            value = currentNoteText,
            onValueChange = { newText -> currentNoteText = newText },
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(top = 16.dp),
            textStyle = TextStyle(textAlign = TextAlign.Start, fontSize = 18.sp, lineHeight = 32.sp),
            placeholder = { Text("Enter your note here...") }
        )
    }
}