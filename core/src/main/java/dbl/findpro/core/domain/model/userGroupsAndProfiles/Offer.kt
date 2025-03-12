package dbl.findpro.core.domain.model.userGroupsAndProfiles

import java.time.Instant
import java.time.LocalDateTime

data class Offer(
    val offerId: String,
    val publisherId: String, // ID del Particular
    val category: Category,  // 🔹 Ahora usa Category en vez de String
    val description: String,
    val address: Address?,   // 🔹 Dirección en lugar de solo coordenadas
    val coordinates: Coordinates,
    val budget: Double? = null,
    val expectedPerformanceId: String? = null, // ID de PerformanceIndicators
    val state: OfferState,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime?,
    val urgency: Boolean,
    val createdDate: Instant,
    val selectedCandidateId: String? = null
)
