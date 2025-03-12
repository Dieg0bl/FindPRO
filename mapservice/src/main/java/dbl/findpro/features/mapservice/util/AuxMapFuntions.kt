package dbl.findpro.features.mapservice.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.mapbox.geojson.Point
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.annotation.generated.*
import dbl.findpro.core.domain.model.userGroupsAndProfiles.*
import dbl.findpro.features.mapservice.data.services.MapboxCircleService
import timber.log.Timber
import kotlin.math.*

/**
 * 📌 Agrega imágenes de marcadores al estilo del mapa, usando iconos específicos para cada categoría.
 */
fun addMapImages(style: Style, categories: List<Category>, context: Context) {
    try {
        // 🔹 Agregar imágenes de usuario y ubicación seleccionada
        if (style.getStyleImage("user_marker") == null) {
            style.addImage("user_marker", createDefaultMarker(Color.BLUE), false)
            style.addImage("selected_marker", createDefaultMarker(Color.GREEN), false)
        }

        // 🔹 Agregar imágenes específicas para cada categoría
        categories.forEach { category ->
            val categoryIcon = getBitmapFromVectorDrawable(context, category.icon)
            if (categoryIcon != null) {
                style.addImage(category.categoryId, categoryIcon, false)
                Timber.d("✅ Icono agregado para categoría: ${category.categoryId}")
            } else {
                Timber.e("❌ Error al cargar el icono para la categoría: ${category.categoryId}")
            }
        }
    } catch (e: Exception) {
        Timber.e("❌ Error al agregar imágenes: ${e.message}")
    }
}

/**
 * 📌 Actualiza los marcadores en función del modo de visualización.
 */
fun updateAnnotations(
    pointAnnotationManager: PointAnnotationManager,
    polygonAnnotationManager: PolygonAnnotationManager,
    userCoordinates: Coordinates?,
    professionals: List<ProfessionalProfile>,
    offers: List<Offer>,
    selectedLocation: Coordinates?,
    mapMode: MapMode,
    focusedProfessional: ProfessionalProfile? = null
) {
    pointAnnotationManager.deleteAll()
    polygonAnnotationManager.deleteAll() // ✅ Limpiar el polígono antes de dibujar uno nuevo

    // 🔵 Marcador del usuario
    userCoordinates?.let {
        pointAnnotationManager.create(
            PointAnnotationOptions()
                .withPoint(Point.fromLngLat(it.longitude, it.latitude))
                .withIconImage("user_marker")
        )
    }

    when (mapMode) {
        MapMode.PROFESSIONALS -> {
            professionals.forEach { professional ->
                val categoryIcon = professional.category.categoryId // ✅ Obtener icono de la categoría
                pointAnnotationManager.create(
                    PointAnnotationOptions()
                        .withPoint(Point.fromLngLat(professional.coordinates.longitude, professional.coordinates.latitude))
                        .withIconImage(categoryIcon) // ✅ Icono específico de la categoría
                )
                Timber.d("✅ Marcador agregado para profesional ${professional.profileId} con icono $categoryIcon")
            }
        }

        MapMode.OFFERS -> {
            offers.forEach { offer ->
                val categoryIcon = offer.category.categoryId // ✅ Obtener icono de la categoría
                offer.coordinates.let {
                    pointAnnotationManager.create(
                        PointAnnotationOptions()
                            .withPoint(Point.fromLngLat(it.longitude, it.latitude))
                            .withIconImage(categoryIcon) // ✅ Icono específico de la categoría
                    )
                    Timber.d("✅ Marcador agregado para oferta ${offer.offerId} con icono $categoryIcon")
                }
            }
        }

        MapMode.SELECT_LOCATION -> {
            selectedLocation?.let {
                pointAnnotationManager.create(
                    PointAnnotationOptions()
                        .withPoint(Point.fromLngLat(it.longitude, it.latitude))
                        .withIconImage("selected_marker")
                )
            }
        }

        MapMode.PROFESSIONAL_FOCUS -> {
            focusedProfessional?.let { professional ->
                // 📍 Marcador del profesional
                val categoryIcon = professional.category.categoryId
                pointAnnotationManager.create(
                    PointAnnotationOptions()
                        .withPoint(Point.fromLngLat(professional.coordinates.longitude, professional.coordinates.latitude))
                        .withIconImage(categoryIcon) // ✅ Icono específico de la categoría
                )

                // 🎯 Dibujar su radio de acción con el `PolygonAnnotationManager`
                drawProfessionalCoverage(polygonAnnotationManager, professional)

                // 🔎 Filtrar ofertas dentro del radio de cobertura
                offers.filter { offer ->
                    offer.coordinates.let {
                        distanceBetween(
                            professional.coordinates.latitude, professional.coordinates.longitude,
                            it.latitude, it.longitude
                        ) <= professional.coverageRadius
                    }
                }.forEach { offer ->
                    val offerCategoryIcon = offer.category.categoryId
                    pointAnnotationManager.create(
                        PointAnnotationOptions()
                            .withPoint(Point.fromLngLat(offer.coordinates.longitude, offer.coordinates.latitude))
                            .withIconImage(offerCategoryIcon) // ✅ Icono específico de la categoría
                    )
                    Timber.d("✅ Oferta ${offer.offerId} dentro del radio de ${professional.coverageRadius} km con icono $offerCategoryIcon")
                }
            }
        }
    }
}

/**
 * 🎯 🔵 Dibuja el radio de cobertura de un profesional.
 */
fun drawProfessionalCoverage(
    polygonAnnotationManager: PolygonAnnotationManager,
    professional: ProfessionalProfile
) {
    val circleService = MapboxCircleService()
    val circleCoordinates = circleService.generateCircle(
        center = Pair(professional.coordinates.latitude, professional.coordinates.longitude),
        radiusKm = professional.coverageRadius.toDouble()
    ).map { Point.fromLngLat(it.second, it.first) }

    polygonAnnotationManager.create(
        PolygonAnnotationOptions()
            .withPoints(listOf(circleCoordinates))
            .withFillColor(Color.argb(100, 0, 255, 0))
            .withFillOpacity(0.3)
    )
}

/**
 * 🔹 Crea un marcador circular con caché.
 */
private val markerCache = mutableMapOf<Int, Bitmap>()

fun createDefaultMarker(color: Int): Bitmap {
    return markerCache.getOrPut(color) {
        val diameter = 48
        val bitmap = Bitmap.createBitmap(diameter, diameter, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply { this.color = color }
        canvas.drawCircle(diameter / 2f, diameter / 2f, diameter / 2f, paint)
        bitmap
    }
}

/**
 * 📌 Convierte un vector drawable en un `Bitmap` para los iconos de categoría.
 */
fun getBitmapFromVectorDrawable(context: Context, drawableResId: Int, targetSize: Int = 96): Bitmap? {
    val drawable: Drawable? = ContextCompat.getDrawable(context, drawableResId)
    drawable ?: return null

    val bitmap = Bitmap.createBitmap(targetSize, targetSize, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)

    // Ajustamos el tamaño del drawable al tamaño objetivo
    drawable.setBounds(0, 0, targetSize, targetSize)
    drawable.draw(canvas)

    return bitmap
}


/**
 * 📏 Calcula la distancia entre dos coordenadas en kilómetros.
 */
fun distanceBetween(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    val earthRadius = 6371.0 // Radio de la Tierra en km
    val dLat = Math.toRadians(lat2 - lat1)
    val dLon = Math.toRadians(lon2 - lon1)
    val a = sin(dLat / 2) * sin(dLat / 2) +
            cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
            sin(dLon / 2) * sin(dLon / 2)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))
    return earthRadius * c
}
