package com.example.myassignment.data.repositories

import androidx.annotation.WorkerThread
import com.example.myassignment.data.daos.TodosDao
import com.example.myassignment.data.entities.Todo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class TodosRepository @Inject constructor(
    private val todosDao: TodosDao
) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allTodos: Flow<List<Todo>> = todosDao.getAllTodos()

    @WorkerThread
    suspend fun getTodo(id: Int) : Todo {
        return todosDao.getTodo(id)
    }


    @WorkerThread
    suspend fun insertTodo(todo: Todo) = todosDao.insertTodo(todo)


    @WorkerThread
    suspend fun updateTodo(todo: Todo) = todosDao.updateTodo(todo)


    @WorkerThread
    suspend fun deleteTodo(todo: Todo) = todosDao.deleteTodo(todo)

}