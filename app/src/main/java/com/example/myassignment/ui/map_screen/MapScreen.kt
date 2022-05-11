package com.example.myassignment.ui.map_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.myassignment.ui.Screen
import com.example.myassignment.ui.add_or_edit_todo_screen.components.CloseIcon
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker


@Composable
fun MapScreen(
    viewModel: MapViewModel = hiltViewModel(),
    navController: NavHostController
) {
    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize()
        ) {
            viewModel.location.value?.let {
                Marker(
                    position = LatLng(it.latitude, it.longitude),
                    title = it.name
                )
            }
        }
        CloseIcon { navController.popBackStack(Screen.TodosScreen.route, inclusive = false) }
    }
}