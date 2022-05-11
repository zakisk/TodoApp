package com.example.myassignment.domain.model

import android.net.Uri
import androidx.room.TypeConverter
import java.util.*

class Converter {

    @TypeConverter
    fun fromTimestamp(value: Long): Date = Date(value)

    @TypeConverter
    fun dateToTimestamp(date: Date): Long = date.time

    @TypeConverter
    fun uriToString(uri: Uri?) : String? = uri?.toString()

    @TypeConverter
    fun stringToUri(str: String?) : Uri? = if (str != null) Uri.parse(str) else null

}