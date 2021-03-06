package com.example.myassignment.ui.add_or_edit_todo_screen

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myassignment.data.entities.Todo
import com.example.myassignment.data.repositories.TodosRepository
import com.example.myassignment.domain.model.Location
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


@HiltViewModel
class AddOrUpdateViewModel @Inject constructor(
    private val repository: TodosRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    init {
        savedStateHandle.get<Int>(ID)?.let {
            viewModelScope.launch {
                if (it != -1) {
                    val todo = repository.getTodo(it)
                    id = todo.id
                    title.value = todo.title
                    description.value = todo.description
                    date.value = todo.date
                    location.value = todo.location
                    imageUri.value = todo.imageUri
                    isDone.value = todo.isDone
                }
            }
        }
    }

    private var id: Int = -1
    val title: MutableState<String> = mutableStateOf("")
    val description: MutableState<String> = mutableStateOf("")
    private val date: MutableState<Date> = mutableStateOf(Date())
    val location: MutableState<Location?> = mutableStateOf(null)
    val imageUri: MutableState<Uri?> = mutableStateOf(null)
    val isDone: MutableState<Boolean> = mutableStateOf(false)


    fun validate() : String {
        var result = ""
        if (title.value.length < 5) result += "Please fill title\n"
        if (description.value.length < 10) result += "Please fill description\n"
        if (imageUri.value == null) result += "Please set image\n"
        if (location.value == null) result += "Please set location"
        return result
    }


    fun insertTodo() = viewModelScope.launch {
        val todo = Todo(
            id = 0,
            title = title.value,
            description = description.value,
            date = date.value,
            location = location.value,
            imageUri = imageUri.value
        )

        repository.insertTodo(todo)
    }

    fun updateTodo() = viewModelScope.launch {
        val todo = Todo(
            id = id,
            title = title.value,
            description = description.value,
            date = date.value,
            location = location.value,
            imageUri = imageUri.value,
            isDone = isDone.value
        )

        repository.updateTodo(todo)
    }

    companion object {
        const val ID = "id"
    }
}