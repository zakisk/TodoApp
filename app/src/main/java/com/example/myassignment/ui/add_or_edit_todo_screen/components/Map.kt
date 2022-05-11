package com.example.myassignment.ui.add_or_edit_todo_screen.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import com.example.myassignment.domain.model.Location
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun Map(
    location: MutableState<Location?>,
    onMapClicked: ((LatLng) -> Unit)? = null
) {
    val cameraPositionState = rememberCameraPositionState()

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        onMapClick = { onMapClicked?.invoke(it) }

    ) {
        location.value?.let {
            val mark = LatLng(it.latitude, it.longitude)
            Marker(
                position = mark,
                title = it.name
            )
        }
    }
}