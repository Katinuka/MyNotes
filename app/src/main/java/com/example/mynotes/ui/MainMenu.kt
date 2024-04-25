package com.example.mynotes.ui


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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mynotes.data.Note
import com.example.mynotes.data.NoteViewModel
import com.example.mynotes.data.formatTitle
import com.example.mynotes.data.toLocalDateTime
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

@Composable
fun MainMenu(
    viewModel: NoteViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navController: NavHostController = rememberNavController()
) {
    val uiState by viewModel.state.collectAsState()

    NavHost(
        navController = navController,
        startDestination = "MainMenu"
    ) {
        composable(route = "MainMenu") {
            Column {
                TopBar(viewModel)

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(uiState) { note ->
                        NoteCard(viewModel, navController, created = note.created)
                    }
                }
            }
        }

        composable(route = "NoteEditor/{created}") {
            val created = it.arguments?.getString("created")?.toLong() ?: 0

            NoteEditor(
                navController = navController,
                viewModel = viewModel,
                created = created)
        }
    }
}

@Composable
fun TopBar(viewModel: NoteViewModel) {
    val coroutineScope = rememberCoroutineScope()

    TopAppBar(
        title = {},
        modifier = Modifier.height(64.dp),
        colors = topAppBarColors(MaterialTheme.colorScheme.primary),
        actions = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "My Notes",
                    fontSize = 24.sp,
                    style = TextStyle(color = Color.White),
                    modifier = Modifier.padding(top = 8.dp)
                )
                Button(onClick = {
                    coroutineScope.launch {
                        viewModel.addNote()
                    }
                }, modifier = Modifier.padding(top = 4.dp)) {
                    Text(text = "+", fontSize = 16.sp)
                }
            }
        }
    )
}

@Composable
fun NoteCard(
    viewModel: NoteViewModel,
    navController: NavHostController,
    created: Long,
) {
    var isDropdownMenuVisible by remember { mutableStateOf(false) }

    // render the dropdown menu
    if (isDropdownMenuVisible) {
        NoteDropdownMenu(viewModel, created, onDismissRequest = { isDropdownMenuVisible = false})
    }
    val uiState by viewModel.state.collectAsState()
    val newRoute = "NoteEditor/$created"

    // render the card
    Card(
        modifier = Modifier.pointerInput(Unit) { detectTapGestures(
            onTap = {
                navController.navigate(newRoute)
            }, 
            onLongPress = { isDropdownMenuVisible = true }
        )},
    ) {
        val currentNote = uiState.find { it.created == created } ?: Note("Error")
        Column {
            Text(
                text = if (currentNote.title != "") currentNote.title else formatTitle(currentNote.text),
                modifier = Modifier
                    .padding(16.dp)
                    .height(32.dp),
                style = TextStyle(fontSize = 18.sp)
            )
            Text(
                text = currentNote.created.toLocalDateTime().format(DateTimeFormatter.ofPattern("d MMM uuuu")),
                modifier = Modifier.padding(4.dp),
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}