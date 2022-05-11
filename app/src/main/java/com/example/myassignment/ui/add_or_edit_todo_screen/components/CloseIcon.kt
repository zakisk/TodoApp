package com.example.myassignment.ui.add_or_edit_todo_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.example.myassignment.ui.theme.LocalSpacing


@Composable
fun BoxScope.CloseIcon(onClick: () -> Unit) {
    val spacing = LocalSpacing.current
    Icon(
        imageVector = Icons.Default.Close,
        contentDescription = null,
        modifier = androidx.compose.ui.Modifier
            .padding(spacing.medium)
            .align(Alignment.TopEnd)
            .background(color = Color.LightGray, shape = CircleShape)
            .clip(CircleShape)
            .clickable { onClick() }
            .padding(spacing.medium)
    )
}