package com.example.myassignment.ui.todos_screen.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myassignment.ui.theme.LocalSpacing
import com.example.myassignment.ui.theme.PrussianBlue

@Composable
fun MyFloatingActionButton(onClick: () -> Unit) {

    val spacing = LocalSpacing.current

    FloatingActionButton(
        onClick = { onClick() },
        backgroundColor = Color.PrussianBlue,
        elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 2.dp),
        modifier = Modifier
            .padding(spacing.medium)
    ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = Color.White)
    }
}