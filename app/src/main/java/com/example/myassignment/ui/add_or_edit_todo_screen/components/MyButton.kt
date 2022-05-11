package com.example.myassignment.ui.add_or_edit_todo_screen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.myassignment.ui.theme.LocalSpacing
import com.example.myassignment.ui.theme.PrussianBlue


@Composable
fun MyButton(
    text : String,
    onClick : () -> Unit
) {
    val spacing = LocalSpacing.current
    val defaultModifier = Modifier
        .fillMaxWidth()
        .padding(all = spacing.small)

    Button(
        onClick = { onClick() },
        modifier = defaultModifier,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.PrussianBlue)
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            modifier = defaultModifier,
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }
}