package com.example.myassignment.ui.todos_screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myassignment.domain.model.Location
import com.example.myassignment.ui.add_or_edit_todo_screen.components.CloseIcon
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker


@Composable
fun ReadMap(location: Location, onCloseClicked: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize()
        ) {
            Marker(
                position = LatLng(location.latitude, location.longitude),
                title = location.name
            )
        }
        CloseIcon { onCloseClicked() }
    }
}