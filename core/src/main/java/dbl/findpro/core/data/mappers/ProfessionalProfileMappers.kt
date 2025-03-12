package dbl.findpro.core.data.mappers

import dbl.findpro.core.domain.model.userGroupsAndProfiles.*

// 📌 Conversión de `ProfessionalProfile` a `Map<String, Any>`
fun ProfessionalProfile.toMap(): Map<String, Any> = mapOf(
    "profileId" to profileId,
    "userId" to userId,
    "categoryId" to category.categoryId, // 🔹 Solo almacenamos el ID de la categoría
    "address" to (address?.toMap() ?: emptyMap()), // 🔹 Se usa `toMap()`
    "contactId" to contactId,
    "coverageRadius" to coverageRadius,
    "coordinates" to coordinates.toMap(), // 🔹 Se usa `toMap()`
    "profileProPicture" to (profileProPicture ?: ""),
    "performanceIndicatorsId" to (performanceIndicatorsId ?: ""), // 🔹 Solo guardamos el ID
    "reviewIdsList" to (reviewIdsList ?: emptyList()),
    "calendarId" to (calendarId ?: "")
)

// 📌 Conversión de `Map<String, Any>` a `ProfessionalProfile`
fun Map<String, Any>.toProfessionalProfile(category: Category): ProfessionalProfile = ProfessionalProfile(
    profileId = this["profileId"] as? String ?: "",
    userId = this["userId"] as? String ?: "",
    category = category, // 🔹 Se pasa el objeto `Category`
    address = (this["address"] as? Map<*, *>)?.toAddress(), // 🔹 Conversión segura a `Address`
    contactId = this["contactId"] as? String ?: "",
    coverageRadius = (this["coverageRadius"] as? Number)?.toInt() ?: 0,
    coordinates = (this["coordinates"] as? Map<*, *>)?.toCoordinates() ?: Coordinates(0.0, 0.0), // 🔹 Conversión segura a `Coordinates`
    profileProPicture = this["profileProPicture"] as? String,
    performanceIndicatorsId = this["performanceIndicatorsId"] as? String,
    reviewIdsList = (this["reviewIdsList"] as? List<*>)?.filterIsInstance<String>() ?: emptyList(),
    calendarId = this["calendarId"] as? String
)
