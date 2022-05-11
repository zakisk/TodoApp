package com.example.myassignment.ui.todos_screen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.example.myassignment.ui.theme.LocalSpacing


@Composable
fun DropdownMenuContainer(
    expanded: MutableState<Boolean>,
    onClickItem: (String) -> Unit,
    items: List<String>,
    containerWidth: Dp? = null
) {

    val spacing = LocalSpacing.current

    DropdownMenu(
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false },
        modifier = if (containerWidth != null) Modifier.width(containerWidth) else Modifier
    ) {

        items.forEach { item ->
            DropdownMenuItem(
                onClick = {
                    onClickItem(item)
                    expanded.value = false
                }
            ) {
                Text(
                    text = item,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(spacing.medium),
                    color = Color.Black
                )
            }
        }
    }

}