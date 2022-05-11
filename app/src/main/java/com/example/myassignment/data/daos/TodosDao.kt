package com.example.myassignment.data.daos

import androidx.room.*
import com.example.myassignment.data.entities.Todo
import kotlinx.coroutines.flow.Flow


@Dao
interface TodosDao {

    @Query("SELECT * FROM todo_table")
    fun getAllTodos() : Flow<List<Todo>>

    @Query("SELECT * FROM todo_table WHERE id = :id")
    suspend fun getTodo(id: Int) : Todo

    @Insert
    suspend fun insertTodo(todo: Todo)

    @Update
    suspend fun updateTodo(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)

}