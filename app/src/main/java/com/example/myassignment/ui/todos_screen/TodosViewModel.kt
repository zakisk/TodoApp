package com.example.myassignment.ui.todos_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myassignment.data.entities.Todo
import com.example.myassignment.data.repositories.TodosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TodosViewModel @Inject constructor(
    private val repository: TodosRepository
) : ViewModel() {

    var allTodos: Flow<List<Todo>> = repository.allTodos



    fun onOptionMenuSelected(action: Int, todo: Todo) = viewModelScope.launch {
        when(action) {
            ACTION_DONE_TASK -> {
                todo.isDone = true
                repository.updateTodo(todo)
                allTodos = repository.allTodos
            }
            ACTION_REMOVE_IMAGE -> {
                todo.imageUri = null
                repository.updateTodo(todo)
            }
            ACTION_REMOVE_LOCATION -> {
                todo.location = null
                repository.updateTodo(todo)
            }
            ACTION_DELETE_TASK -> {
                repository.deleteTodo(todo)
            }
        }
    }


    companion object {
        const val ACTION_DONE_TASK = 0

        const val ACTION_REMOVE_IMAGE = 1

        const val ACTION_REMOVE_LOCATION = 2

        const val ACTION_DELETE_TASK = 3
    }

}