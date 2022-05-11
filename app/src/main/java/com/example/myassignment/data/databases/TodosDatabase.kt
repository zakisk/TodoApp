package com.example.myassignment.data.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myassignment.data.daos.TodosDao
import com.example.myassignment.data.entities.Todo


@Database(entities = [Todo::class], version = 4)
abstract class TodosDatabase : RoomDatabase() {
    abstract fun todosDao(): TodosDao
}