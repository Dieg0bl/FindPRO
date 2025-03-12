package dbl.findpro.features.mapservice.data.services

import kotlin.math.*

class MapboxCircleService {
    fun generateCircle(
        center: Pair<Double, Double>,
        radiusKm: Double,
        numPoints: Int = 64
    ): List<Pair<Double, Double>> { // ✅ Asegurar que devuelve `Pair<Double, Double>`
        val (centerLat, centerLon) = center
        val earthRadius = 6371.0 // Radio de la Tierra en km

        val centerLatRad = Math.toRadians(centerLat)
        val centerLonRad = Math.toRadians(centerLon)

        val circlePoints = mutableListOf<Pair<Double, Double>>() // ✅ Lista de `Pair<Double, Double>`

        for (i in 0 until numPoints) {
            val angle = 2 * Math.PI * i / numPoints

            val pointLatRad = asin(
                sin(centerLatRad) * cos(radiusKm / earthRadius) +
                        cos(centerLatRad) * sin(radiusKm / earthRadius) * cos(angle)
            )

            val pointLonRad = centerLonRad + atan2(
                sin(angle) * sin(radiusKm / earthRadius) * cos(centerLatRad),
                cos(radiusKm / earthRadius) - sin(centerLatRad) * sin(pointLatRad)
            )

            val pointLat = Math.toDegrees(pointLatRad)
            val pointLon = Math.toDegrees(pointLonRad)

            circlePoints.add(Pair(pointLat, pointLon)) // ✅ Guardamos en formato `Pair`
        }

        return circlePoints
    }
}
