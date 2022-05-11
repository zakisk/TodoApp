package com.example.myassignment.data.entities

import android.net.Uri
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.myassignment.domain.model.Converter
import com.example.myassignment.domain.model.Location
import java.util.*


@Entity(tableName = "todo_table")
@TypeConverters(Converter::class)
data class Todo(
    @PrimaryKey(autoGenerate = true) var id: Int,
    val title: String,
    val description: String,
    var date: Date,
    @Embedded var location: Location?,
    var imageUri: Uri?,
    var isDone: Boolean = false
)