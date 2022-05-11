package com.example.myassignment.ui.todos_screen.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.myassignment.ui.theme.LocalSpacing
import com.example.myassignment.util.asBitmap


@Composable
fun TodoImage(
    uri: Uri?,
    isDone: Boolean,
    widthFraction: Float = 0.3f,
    height: Dp = 130.dp,
    onClick: (() -> Unit)? = null
) {

    val shape = RoundedCornerShape(4.dp)
    val spacing = LocalSpacing.current
    val modifier = Modifier
        .fillMaxWidth(widthFraction)
        .border(width = 0.3.dp, color = Color.LightGray, shape = shape)
        .background(color = Color.White, shape = shape)
        .clip(shape)
        .height(height)
        .padding(spacing.medium)
        .clickable {
            if (!isDone) {
                onClick?.invoke()
            }
        }

    val context = LocalContext.current
    val image = uri?.asBitmap(context)

    when {
        isDone -> {
            Image(
                imageVector = Icons.Default.Done,
                contentDescription = null,
                modifier = modifier
            )
        }
        image != null -> {
            Image(
                bitmap = image.asImageBitmap(),
                contentDescription = null,
                modifier = modifier
            )
        }
        else -> {
            Image(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                modifier = modifier
            )
        }
    }
}