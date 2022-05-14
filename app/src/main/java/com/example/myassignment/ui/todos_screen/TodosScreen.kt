package com.example.myassignment.ui.todos_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.myassignment.data.entities.Todo
import com.example.myassignment.ui.Screen
import com.example.myassignment.ui.theme.LocalSpacing
import com.example.myassignment.ui.todos_screen.components.Div
import com.example.myassignment.ui.todos_screen.components.EmptyList
import com.example.myassignment.ui.todos_screen.components.MyFloatingActionButton
import com.example.myassignment.ui.todos_screen.components.TodoListItem
import com.example.myassignment.util.showToast


@Composable
fun TodosScreen(
    viewModel: TodosViewModel = hiltViewModel(),
    navController: NavHostController
) {

    val spacing = LocalSpacing.current
    val actions = listOf("Task Done", "Remove Image", "Remove Location", "Delete Task")
    val context = LocalContext.current
    val onItemClicked: (Todo) -> Unit = {
        navController.navigate(Screen.AddOrUpdateTodoScreen.route + "/${it.id}")
    }

    val onOptionsMenuSelected: (String, Todo) -> Unit = { action, todo ->
        viewModel.onOptionMenuSelected(actions.indexOf(action), todo)
        if (action != actions[0]) {
            context.showToast("Successfully Removed")
        }
    }

    val onLocationClicked: (Todo) -> Unit = {
        navController.navigate(Screen.MapScreen.route + "/${it.id}")
    }

    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry.value?.destination?.route

    Scaffold(floatingActionButton = {
        if (currentRoute == Screen.TodosScreen.route) {
            MyFloatingActionButton { navController.navigate(Screen.AddOrUpdateTodoScreen.route + "/-1") }
        }
    }) {
        Box(modifier = Modifier.fillMaxSize()) {
            val allTodos = viewModel.allTodos.collectAsState(emptyList())

            LazyColumn {
                item {
                    Text(
                        text = "TODOs",
                        style = MaterialTheme.typography.h4,
                        fontWeight = FontWeight.W900,
                        fontFamily = FontFamily.Monospace,
                        modifier = Modifier.padding(spacing.medium)
                    )

                    Div()
                }
                items(allTodos.value) { todo ->
                    TodoListItem(
                        todo = todo,
                        actions = actions,
                        onItemClicked = onItemClicked,
                        onOptionsMenuSelected = onOptionsMenuSelected,
                        onLocationClicked = onLocationClicked
                    )
                    Div()
                }
            }

            if (allTodos.value.isEmpty()) {
                EmptyList()
            }

        }
    }

}