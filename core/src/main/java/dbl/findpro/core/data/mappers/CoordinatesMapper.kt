package dbl.findpro.core.data.mappers

import dbl.findpro.core.domain.model.userGroupsAndProfiles.Coordinates

// 📌 Convierte un `Coordinates` a `Map<String, Any>`
fun Coordinates.toMap(): Map<String, Any> = mapOf(
    "latitude" to latitude,
    "longitude" to longitude
)

// 📌 Convierte un `Map<String, Any>` de Firestore a `Coordinates` con Conversión Segura
fun Map<*, *>.toCoordinates(): Coordinates {
    val safeMap = this.mapKeys { it.key.toString() } // 🔹 Convierte claves a String de forma segura
    return Coordinates(
        latitude = (safeMap["latitude"] as? Number)?.toDouble() ?: 0.0,
        longitude = (safeMap["longitude"] as? Number)?.toDouble() ?: 0.0
    )
}
