package dbl.findpro.core.data.mappers

import dbl.findpro.core.domain.model.userGroupsAndProfiles.ParticularProfile
import dbl.findpro.core.domain.model.userGroupsAndProfiles.ProfileType
import timber.log.Timber

fun ParticularProfile.toMap(): Map<String, Any> = mapOf(
    "userId" to userId,
    "profileId" to profileId,
    "contactId" to contactId,
    "address" to (address?.toMap() ?: ""),
    "coordinates" to (coordinates?.toMap() ?: ""),
    "offerIdsList" to offerIdsList.map { it }
)


fun Map<String, Any>.toParticularProfile(): ParticularProfile? {
    return try {
        ParticularProfile(
            userId = this["userId"] as? String ?: return null,
            profileId = this["profileId"] as? String ?: return null,
            profileType = ProfileType.PARTICULAR,
            address = (this["address"] as? Map<*, *>)?.toAddress(), // ✅ Conversión segura de dirección
            coordinates = (this["coordinates"] as? Map<*, *>)?.toCoordinates(), // ✅ Conversión segura de coordenadas
            contactId = this["contactId"] as? String ?: return null,
            offerIdsList = (this["offerIdsList"] as? List<*>)?.filterIsInstance<String>() ?: emptyList() // ✅ Evita `Unchecked Cast`
        )
    } catch (e: Exception) {
        Timber.e(e, "❌ Error al mapear el documento a ParticularProfile")
        null
    }
}
