package dbl.findpro.features.mapservice.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import dbl.findpro.core.domain.model.userGroupsAndProfiles.*
import dbl.findpro.features.mapservice.presentation.ui.components.*

@Composable
fun SelectLocationScreen(
    onLocationSelected: (Coordinates) -> Unit
) {
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        MapViewComponent(
            context = context,
            userCoordinates = null,
            professionals = emptyList(),
            offers = emptyList(),
            selectedLocation = null,
            onLocationSelected = onLocationSelected, // 📌 Acción al seleccionar ubicación
            mapMode = MapMode.SELECT_LOCATION, // 📌 Modo fijo
            focusedProfessional = null,
            categories = emptyList()
        )
    }
}
