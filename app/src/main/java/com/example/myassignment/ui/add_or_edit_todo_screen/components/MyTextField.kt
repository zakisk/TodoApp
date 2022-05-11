package com.example.myassignment.ui.add_or_edit_todo_screen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.myassignment.ui.theme.LocalSpacing
import com.example.myassignment.ui.theme.PrussianBlue


@Composable
fun MyTextField(
    text: MutableState<String>,
    placeholderText: String,
    singleLine: Boolean = true,
    widthFraction: Float = 1f
) {

    val spacing = LocalSpacing.current

    val defaultModifier = Modifier
        .fillMaxWidth(widthFraction)
        .padding(all = spacing.small)

    val textFieldDefaults = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = Color.PrussianBlue,
        cursorColor = Color.PrussianBlue
    )


    OutlinedTextField(
        value = text.value,
        onValueChange = {
            text.value = it
        },
        modifier = defaultModifier,
        placeholder = { Text(placeholderText) },
        singleLine = singleLine,
        colors = textFieldDefaults
    )


}