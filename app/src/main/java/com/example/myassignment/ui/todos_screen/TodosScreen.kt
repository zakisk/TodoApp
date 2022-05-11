package com.example.myassignment.ui.todos_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.myassignment.data.entities.Todo
import com.example.myassignment.ui.Screen
import com.example.myassignment.ui.theme.LocalSpacing
import com.example.myassignment.ui.theme.PrussianBlue
import com.example.myassignment.ui.todos_screen.components.Div
import com.example.myassignment.ui.todos_screen.components.EmptyList
import com.example.myassignment.ui.todos_screen.components.MyFloatingActionButton
import com.example.myassignment.ui.todos_screen.components.TodoListItem


//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

@Composable
fun TodosScreen(
    viewModel: TodosViewModel = hiltViewModel(),
    navController: NavHostController
) {

    val spacing = LocalSpacing.current
    val actions = listOf("Task Done", "Remove Image", "Remove Location", "Delete Task")
    val showProgressBar = remember { mutableStateOf(false) }
    val onItemClicked: (Todo) -> Unit = {
        navController.navigate(Screen.AddOrUpdateTodoScreen.route + "/${it.id}")
    }

    val onOptionsMenuSelected: (String, Todo) -> Unit = { action, todo ->
        viewModel.onOptionMenuSelected(actions.indexOf(action), todo)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "TODOs",
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.W900,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier.padding(spacing.medium)
            )

            Div()

            Box(modifier = Modifier.fillMaxSize()) {

                val allTodos = viewModel.allTodos.collectAsState(emptyList())

                LazyColumn(modifier = Modifier.padding(bottom = 96.dp)) {
                    showProgressBar.value = true
                    items(allTodos.value) { todo ->
                        TodoListItem(
                            todo = todo,
                            actions = actions,
                            onItemClicked = onItemClicked,
                            onOptionsMenuSelected = onOptionsMenuSelected
                        )
                        Div()
                    }
                    showProgressBar.value = false
                }

                if (allTodos.value.isEmpty()) {
                    EmptyList()
                }
            }
        }

        if (showProgressBar.value) {
            CircularProgressIndicator(
                color = Color.PrussianBlue, modifier = Modifier.align(Alignment.Center)
            )
        }

        MyFloatingActionButton { navController.navigate(Screen.AddOrUpdateTodoScreen.route + "/-1") }
    }

}