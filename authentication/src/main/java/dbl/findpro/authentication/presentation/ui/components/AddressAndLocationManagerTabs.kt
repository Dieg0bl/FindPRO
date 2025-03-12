package dbl.findpro.authentication.presentation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dbl.findpro.core.domain.model.userGroupsAndProfiles.Address
import dbl.findpro.core.domain.model.userGroupsAndProfiles.Coordinates
import dbl.findpro.features.mapservice.presentation.ui.components.MapViewComponent

@Composable
fun AddressAndLocationManagerTabs(
    selectedTabIndex: Int,
    onTabChange: (Int) -> Unit,
    initialAddress: Address,
    initialCoordinates: Coordinates,
    initialRadius: Float,
    showSearchBar: Boolean,
    showRadiusSlider: Boolean,
    onAddressChange: (Address) -> Unit,
    onCoordinatesChange: (Coordinates) -> Unit,
    onRadiusChange: (Int) -> Unit,
) {
    var address by remember { mutableStateOf(initialAddress) }
    var coordinates by remember { mutableStateOf(initialCoordinates) }
    var radius by remember { mutableFloatStateOf(initialRadius) }
    var validate by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        // Tabs para alternar entre dirección y mapa
        TabRow(selectedTabIndex = selectedTabIndex) {
            Tab(
                selected = selectedTabIndex == 0,
                onClick = { onTabChange(0) },
                text = { Text("Introducir Dirección") }
            )
            Tab(
                selected = selectedTabIndex == 1,
                onClick = { onTabChange(1) },
                text = { Text("Seleccionar en Mapa") }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (selectedTabIndex) {
            0 -> {
                // 📌 Pestaña de Dirección
                AddressFields(
                    address = address,
                    showRadiusSlider = showRadiusSlider,
                    radius = radius,
                    onRadiusChange = { updatedRadius ->
                        radius = updatedRadius
                        onRadiusChange(updatedRadius.toInt())
                    },
                    validate = validate,
                    onAddressChange = { updatedAddress ->
                        address = updatedAddress
                        onAddressChange(updatedAddress)
                    },
                    onGeocodeResult = { newCoordinates ->
                        newCoordinates?.let {
                            coordinates = it
                            onCoordinatesChange(it)
                        }
                    },
                    onGeocodeRequest = { /* 🚀 Manejar geocodificación desde fuera */ },
                )
            }
            1 -> {
                MapViewComponent(
                    context = TODO(),
                    userCoordinates = TODO(),
                    offers = TODO(),
                    selectedLocation = TODO(),
                    onLocationSelected = TODO(),
                    mapMode = TODO(),
                    focusedProfessional = TODO(),
                    categories = TODO(),
                )
            }
        }
    }
}
