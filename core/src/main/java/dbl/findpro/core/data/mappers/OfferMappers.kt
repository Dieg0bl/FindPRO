package dbl.findpro.core.data.mappers

import dbl.findpro.core.domain.model.userGroupsAndProfiles.*
import java.time.Instant
import java.time.LocalDateTime

// 📌 Conversión de `Offer` a `Map<String, Any>`
fun Offer.toMap(): Map<String, Any> = mapOf(
    "offerId" to offerId,
    "publisherId" to publisherId,
    "categoryId" to category.categoryId, // 🔹 Solo se almacena el ID
    "description" to description,
    "address" to (address?.toMap() ?: emptyMap()), // 🔹 Se usa `toMap()`
    "coordinates" to coordinates.toMap(), // 🔹 Se usa `toMap()`
    "budget" to (budget ?: 0.0),
    "expectedPerformanceId" to (expectedPerformanceId ?: ""),
    "state" to state.name,
    "startDate" to startDate.toEpochMilli(), // 🔹 Se usa `toEpochMilli()`
    "endDate" to (endDate?.toEpochMilli() ?: 0L),
    "urgency" to urgency,
    "createdDate" to createdDate.toFirestoreTimestamp(), // 🔹 Se usa `toFirestoreTimestamp()`
    "selectedCandidateId" to (selectedCandidateId ?: "")
)

// 📌 Conversión de `Map<String, Any>` a `Offer`
fun Map<String, Any>.toOffer(category: Category): Offer {
    val categoryId = this["categoryId"] as? String ?: return throw IllegalArgumentException("categoryId es nulo")
    return Offer(
        offerId = this["offerId"] as String,
        publisherId = this["publisherId"] as String,
        category = category,
        description = this["description"] as String,
        address = (this["address"] as? Map<*, *>)?.toAddress(),
        coordinates = (this["coordinates"] as? Map<*, *>)?.toCoordinates() ?: Coordinates(0.0, 0.0),
        budget = (this["budget"] as? Number)?.toDouble(),
        expectedPerformanceId = this["expectedPerformanceId"] as? String,
        state = OfferState.valueOf(this["state"] as String),
        startDate = (this["startDate"] as? Long)?.toInstantFromFirestore()?.toLocalDateTime() ?: LocalDateTime.now(),
        endDate = (this["endDate"] as? Long)?.toInstantFromFirestore()?.toLocalDateTime(),
        urgency = this["urgency"] as Boolean,
        createdDate = (this["createdDate"] as? Long)?.toInstantFromFirestore() ?: Instant.now(),
        selectedCandidateId = this["selectedCandidateId"] as? String
    )
}

