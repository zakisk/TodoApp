package com.example.myassignment.di

import android.content.Context
import androidx.room.Room
import com.example.myassignment.data.daos.TodosDao
import com.example.myassignment.data.databases.TodosDatabase
import com.example.myassignment.data.repositories.TodosRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesTodosDatabase(@ApplicationContext appContext: Context): TodosDatabase {
        return Room.databaseBuilder(
            appContext,
            TodosDatabase::class.java,
            "TodosDatabase"
        )
        .fallbackToDestructiveMigration()
        .build()
    }

    @Singleton
    @Provides
    fun providesTodosDao(todosDatabase: TodosDatabase): TodosDao = todosDatabase.todosDao()


    @Singleton
    @Provides
    fun providesTodosRepository(todosDao: TodosDao): TodosRepository {
        return TodosRepository(todosDao)
    }

}