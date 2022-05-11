package com.example.myassignment.ui.map_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myassignment.data.repositories.TodosRepository
import com.example.myassignment.domain.model.Location
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MapViewModel @Inject constructor(
    private val repository: TodosRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    init {
        savedStateHandle.get<Int>(ID)?.let {
            viewModelScope.launch {
                val todo = repository.getTodo(it)
                location.value = todo.location
            }
        }
    }

    val location: MutableState<Location?> = mutableStateOf(null)

    companion object {
        const val ID = "id"
    }
}