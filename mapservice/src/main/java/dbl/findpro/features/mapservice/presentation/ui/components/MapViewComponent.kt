package dbl.findpro.features.mapservice.presentation.ui.components

import android.content.Context
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.viewinterop.AndroidView
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.*
import com.mapbox.maps.plugin.gestures.addOnMapClickListener
import dbl.findpro.core.domain.model.userGroupsAndProfiles.Category
import dbl.findpro.core.domain.model.userGroupsAndProfiles.Coordinates
import dbl.findpro.core.domain.model.userGroupsAndProfiles.MapMode
import dbl.findpro.core.domain.model.userGroupsAndProfiles.Offer
import dbl.findpro.core.domain.model.userGroupsAndProfiles.ProfessionalProfile
import dbl.findpro.features.mapservice.util.addMapImages
import dbl.findpro.features.mapservice.util.distanceBetween
import dbl.findpro.features.mapservice.util.updateAnnotations

@Composable
fun MapViewComponent(
    context: Context,
    userCoordinates: Coordinates?,
    professionals: List<ProfessionalProfile> = emptyList(),
    offers: List<Offer> = emptyList(),
    selectedLocation: Coordinates?,
    onLocationSelected: (Coordinates) -> Unit,
    mapMode: MapMode,
    focusedProfessional: ProfessionalProfile? = null,
    categories: List<Category>
) {
    val zoom = 6.0
    val initialSpainCenter = Point.fromLngLat(-3.7, 40.4)
    var isMapInitialized by rememberSaveable { mutableStateOf(false) }

    // 📌 Filtrar ofertas dentro del radio de cobertura del profesional si está en modo `PROFESSIONAL_FOCUS`
    val filteredOffers = if (mapMode == MapMode.PROFESSIONAL_FOCUS && focusedProfessional != null) {
        offers.filter { offer ->
            val distance = distanceBetween(
                focusedProfessional.coordinates.latitude,
                focusedProfessional.coordinates.longitude,
                offer.coordinates.latitude,
                offer.coordinates.longitude
            )
            distance <= focusedProfessional.coverageRadius
        }
    } else {
        offers
    }

    AndroidView(
        factory = {
            val mapView = MapView(context, MapInitOptions(context))

            // 🔹 Crear los gestores de anotaciones
            val annotationManager = mapView.annotations.createPointAnnotationManager()
            val polygonAnnotationManager = mapView.annotations.createPolygonAnnotationManager() // ✅ Nuevo para polígonos
            mapView.tag = listOf(annotationManager, polygonAnnotationManager)

            mapView.mapboxMap.loadStyle(Style.MAPBOX_STREETS) { style ->
                addMapImages(
                    style,
                    categories = categories,
                    context = context
                )

                if (!isMapInitialized) {
                    mapView.mapboxMap.setCamera(
                        CameraOptions.Builder()
                            .center(initialSpainCenter)
                            .zoom(zoom)
                            .build()
                    )
                    isMapInitialized = true
                }

                // ✅ Ahora pasamos `polygonAnnotationManager`
                updateAnnotations(
                    annotationManager,
                    polygonAnnotationManager,
                    userCoordinates,
                    professionals,
                    filteredOffers,
                    selectedLocation,
                    mapMode,
                    focusedProfessional
                )
            }

            // 🟢 Habilitar selección de ubicación si el modo es `SELECT_LOCATION`
            if (mapMode == MapMode.SELECT_LOCATION) {
                mapView.mapboxMap.addOnMapClickListener { point ->
                    onLocationSelected(Coordinates(point.latitude(), point.longitude()))
                    true
                }
            }

            mapView
        },
        update = { mapView ->
            mapView.mapboxMap.getStyle { style ->
                addMapImages(
                    style,
                    categories = categories,
                    context = context// ✅ Pasamos las categorías reales
                )

                val tagList = mapView.tag as? List<*> ?: return@getStyle
                val annotationManager = tagList.getOrNull(0) as? PointAnnotationManager ?: return@getStyle
                val polygonAnnotationManager = tagList.getOrNull(1) as? PolygonAnnotationManager ?: return@getStyle

                // ✅ Actualizar anotaciones con los dos gestores
                updateAnnotations(
                    annotationManager,
                    polygonAnnotationManager,
                    userCoordinates,
                    professionals,
                    filteredOffers,
                    selectedLocation,
                    mapMode,
                    focusedProfessional
                )
            }
        }
    )
}
