package com.example.myassignment.ui

sealed class Screen(val route: String) {

    object TodosScreen : Screen("todos")

    object AddOrUpdateTodoScreen : Screen("add_or_update_todo")
}
