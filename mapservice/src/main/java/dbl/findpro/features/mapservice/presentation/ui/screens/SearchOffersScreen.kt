package dbl.findpro.features.mapservice.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import dbl.findpro.core.domain.model.userGroupsAndProfiles.*
import dbl.findpro.features.mapservice.presentation.viewmodel.*
import dbl.findpro.features.mapservice.presentation.ui.components.*
import dbl.findpro.features.mapservice.util.distanceBetween

@Composable
fun SearchOffersScreen(
    offerViewModel: OfferViewModel = hiltViewModel(),
    professionalViewModel: ProfessionalViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val allOffers by offerViewModel.offers.collectAsState()
    val professional by professionalViewModel.professional.collectAsState() // ✅ Usamos el flujo `professional`

    val filteredOffers = remember(allOffers, professional) {
        allOffers.filter { offer ->
            professional?.let {
                val distance = distanceBetween(
                    it.coordinates.latitude,
                    it.coordinates.longitude,
                    offer.coordinates.latitude,
                    offer.coordinates.longitude
                )
                distance <= it.coverageRadius
            } == true
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        MapViewComponent(
            context = context,
            userCoordinates = professional?.coordinates,
            professionals = emptyList(),
            offers = filteredOffers,
            selectedLocation = null,
            onLocationSelected = {},
            mapMode = MapMode.OFFERS, // 📌 Modo fijo
            focusedProfessional = professional,
            categories = emptyList()
        )
    }
}
