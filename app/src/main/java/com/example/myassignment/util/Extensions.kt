package com.example.myassignment.util

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.net.toUri
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import me.shouheng.compress.Compress
import me.shouheng.compress.concrete
import me.shouheng.compress.strategy.config.ScaleMode
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


fun Date.asFormatted(): String {
    val spf = SimpleDateFormat("EEE, MMM dd, hh:mm aaa", Locale.getDefault())
    return spf.format(this)
}


@SuppressLint("WrongConstant")
fun Uri.asBitmap(context: Context): Bitmap? {
    return Compress.with(context, this)
        .setQuality(100)
        .concrete {
            withScaleMode(ScaleMode.SCALE_LARGER)
            withIgnoreIfSmaller(true)
        }
        .getBitmap()
}

fun Context.showToast(message: String) {
    MainScope().launch {
        Toast.makeText(this@showToast, message, Toast.LENGTH_LONG).show()
    }
}

fun Context.getUri() : Uri? {
    val filename = "${System.currentTimeMillis()}"
    var uri: Uri? = null
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        this.contentResolver?.also { resolver ->
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            }
            val imageUri: Uri? =
                resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            if (imageUri != null) {
                uri = imageUri
            }
        }
    } else {
        val imagesDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File(imagesDir, filename)
        uri = image.toUri()
    }
    return uri
}