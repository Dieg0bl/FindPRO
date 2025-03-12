package dbl.findpro.features.mapservice.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import dbl.findpro.core.domain.model.userGroupsAndProfiles.*
import dbl.findpro.features.mapservice.presentation.viewmodel.*
import dbl.findpro.features.mapservice.presentation.ui.components.*

@Composable
fun SearchProfessionalsScreen(
    navController: NavHostController,
    professionalViewModel: ProfessionalViewModel = hiltViewModel(),
    categoryViewModel: CategoryViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val allProfessionals by professionalViewModel.professionals.collectAsState()
    val categories by categoryViewModel.categories.collectAsState()
    var selectedCategoryId by remember { mutableStateOf<String?>(null) }

    val filteredProfessionals by produceState(initialValue = allProfessionals, selectedCategoryId, allProfessionals) {
        value = selectedCategoryId?.let { professionalViewModel.filterProfessionalsByCategory(it) } ?: allProfessionals
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            CategoriesBar(
                categories = categories,
                selectedCategoryId = selectedCategoryId,
                onCategorySelected = { selectedCategoryId = it }
            )

            Box(modifier = Modifier.weight(1f)) {
                MapViewComponent(
                    context = context,
                    userCoordinates = null,
                    professionals = filteredProfessionals,
                    offers = emptyList(),
                    selectedLocation = null,
                    onLocationSelected = {},
                    mapMode = MapMode.PROFESSIONALS, // 📌 Modo fijo
                    focusedProfessional = null,
                    categories = categories
                )
            }
        }

        // 🔹 📌 Botón flotante para iniciar sesión (discreto, accesible)
        FloatingActionButton(
            onClick = { navController.navigate("login") },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp),
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ) {
            Icon(Icons.Default.Person, contentDescription = "Iniciar sesión")
        }
    }
}
