package com.example.myassignment.ui.add_or_edit_todo_screen

import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.myassignment.domain.model.Location
import com.example.myassignment.ui.Screen
import com.example.myassignment.ui.add_or_edit_todo_screen.components.CloseIcon
import com.example.myassignment.ui.add_or_edit_todo_screen.components.Map
import com.example.myassignment.ui.add_or_edit_todo_screen.components.MyButton
import com.example.myassignment.ui.add_or_edit_todo_screen.components.MyTextField
import com.example.myassignment.ui.theme.LocalSpacing
import com.example.myassignment.ui.theme.PrussianBlue
import com.example.myassignment.ui.todos_screen.components.TodoImage
import com.example.myassignment.util.Constants.INSERT
import com.example.myassignment.util.showToast
import com.google.android.gms.maps.model.LatLng


@Composable
fun AddOrUpdateTodoScreen(
    viewModel: AddOrUpdateViewModel = hiltViewModel(),
    navController: NavHostController,
    selectImage: () -> Unit,
    uri: Uri?,
    typeOfOperation: String
) {
    uri?.let { viewModel.imageUri.value = it }

    var showMap by remember { mutableStateOf(false) }
    val spacing = LocalSpacing.current
    val onMapClicked: (LatLng) -> Unit = {
        viewModel.location.value =
            Location(it.latitude, it.longitude, viewModel.locationName.value.ifBlank { null })
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                TodoImage(
                    uri = viewModel.imageUri.value,
                    isDone = viewModel.isDone.value,
                    widthFraction = 0.4f,
                    height = 180.dp,
                    onClick = { selectImage() }
                )
            }

            item {
                MyTextField(
                    text = viewModel.title,
                    placeholderText = "Title"
                )

                MyTextField(
                    text = viewModel.description,
                    placeholderText = "Description",
                    singleLine = false
                )

                MyTextField(
                    text = viewModel.locationName,
                    placeholderText = "Location Name"
                )
            }

            item {
                val shape = RoundedCornerShape(4.dp)
                Row(
                    modifier = Modifier
                        .padding(spacing.small)
                        .fillMaxWidth()
                        .border(width = 0.3.dp, color = Color.PrussianBlue, shape = shape)
                        .padding(spacing.small)
                        .clickable {
                            showMap = true
                        },
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = Color.PrussianBlue,
                        modifier = Modifier.padding(spacing.small)
                    )
                    Text(
                        text = if (typeOfOperation == INSERT || viewModel.location.value == null)
                            "Add Location"
                        else
                            "Update Location",
                        modifier = Modifier.padding(spacing.small)
                    )
                }
            }


            item {
                val context = LocalContext.current
                MyButton(text = typeOfOperation) {
                    val isValid = viewModel.title.value.length >= 5 &&
                            viewModel.description.value.length >= 10
                    if (isValid) {
                        if (typeOfOperation == INSERT) {
                            viewModel.insertTodo()
                        } else {
                            viewModel.updateTodo()
                        }
                        navController.popBackStack(Screen.TodosScreen.route, inclusive = false)
                    } else {
                        context.showToast("Please fill all fields")
                    }
                }
            }
        }

        if (showMap) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Map(location = viewModel.location, onMapClicked = onMapClicked)
                CloseIcon { showMap = false }
            }
        }
    }
}