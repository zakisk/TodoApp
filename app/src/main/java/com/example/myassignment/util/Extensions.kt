package com.example.myassignment.util

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import me.shouheng.compress.Compress
import me.shouheng.compress.concrete
import me.shouheng.compress.strategy.config.ScaleMode
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