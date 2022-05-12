package com.example.myassignment.ui.todos_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.myassignment.data.entities.Todo
import com.example.myassignment.ui.theme.LocalSpacing
import com.example.myassignment.util.asFormatted
import com.example.myassignment.util.showToast


@Composable
fun TodoListItem(
    todo: Todo,
    actions: List<String>,
    onItemClicked: (Todo) -> Unit,
    onOptionsMenuSelected: (String, Todo) -> Unit,
    onLocationClicked: (Todo) -> Unit
) {

    val smallSpacing = LocalSpacing.current.small
    val shape = RoundedCornerShape(4.dp)
    val expanded = remember { mutableStateOf(false) }
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .padding(start = smallSpacing, end = smallSpacing, top = smallSpacing)
            .clickable {
                onItemClicked(todo)
            }
    ) {

        TodoImage(uri = todo.imageUri, isDone = todo.isDone)

        Box(
            modifier = Modifier
                .padding(smallSpacing)
                .fillMaxWidth()
                .height(130.dp)
        ) {

            Column(
                modifier = Modifier.align(Alignment.TopStart)
            ) {

                Text(
                    text = todo.date.asFormatted(),
                    style = MaterialTheme.typography.caption,
                    color = Color.LightGray
                )

                Text(
                    text = todo.title,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.body2
                )

                Text(
                    text = todo.description,
                    style = MaterialTheme.typography.caption,
                    overflow = TextOverflow.Ellipsis
                )

            }

            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .clip(CircleShape)
                    .clickable {
                        if (todo.location != null) {
                            onLocationClicked(todo)
                        } else {
                            context.showToast("You haven't set Location yet")
                        }
                    }
                    .padding(smallSpacing)
            )


            Box(modifier = Modifier.align(Alignment.TopEnd)) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable {
                            expanded.value = true
                        }
                )

                DropdownMenuContainer(
                    expanded = expanded,
                    onClickItem = {
                        onOptionsMenuSelected(it, todo)
                    },
                    items = actions
                )
            }


        }

    }

}