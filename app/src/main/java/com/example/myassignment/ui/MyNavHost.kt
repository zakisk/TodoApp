package com.example.myassignment.ui

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.myassignment.ui.add_or_edit_todo_screen.AddOrUpdateTodoScreen
import com.example.myassignment.ui.map_screen.MapScreen
import com.example.myassignment.ui.todos_screen.TodosScreen
import com.example.myassignment.util.Constants.INSERT
import com.example.myassignment.util.Constants.UPDATE

@Composable
fun MyNavHost(
    navController: NavHostController,
    uri: MutableState<Uri?>,
    selectImage: () -> Unit
) {

    navController.addOnDestinationChangedListener { _, _, _ ->
        uri.value = null
    }

    NavHost(
        navController = navController,
        startDestination = Screen.TodosScreen.route
    ) {
        composable(route = Screen.TodosScreen.route) {
            TodosScreen(navController = navController)
        }

        composable(
            route = Screen.AddOrUpdateTodoScreen.route + "/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            val id = it.arguments?.getInt("id")
            val typeOfOperation = if (id == -1) INSERT else UPDATE
            AddOrUpdateTodoScreen(
                navController = navController,
                selectImage = selectImage,
                uri = uri.value,
                typeOfOperation = typeOfOperation
            )
        }

        composable(
            route = Screen.MapScreen.route + "/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            MapScreen(navController = navController)
        }
    }
}