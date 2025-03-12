package dbl.findpro.core.domain.usecase

import dbl.findpro.core.domain.model.userGroupsAndProfiles.Offer
import dbl.findpro.core.domain.model.userGroupsAndProfiles.ProfessionalProfile
import dbl.findpro.core.domain.repository.IOfferRepository
import dbl.findpro.core.domain.repository.IProfessionalProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import kotlin.math.*

class GetProfessionalWithOffersUseCase(
    private val IProfessionalProfileRepository: IProfessionalProfileRepository,
    private val IOfferRepository: IOfferRepository
) {
    suspend operator fun invoke(profileId: String): Pair<ProfessionalProfile?, List<Offer>> {
        return withContext(Dispatchers.IO) {
            val professional = IProfessionalProfileRepository.getAllProfessionals().find { it.profileId == profileId }
            val allOffers = IOfferRepository.getAllOffers()

            if (professional == null) {
                Timber.e("❌ No se encontró un profesional con ID: $profileId")
                return@withContext Pair(null, emptyList())
            }

            val professionalCoordinates = professional.coordinates
            val offersWithinRadius = allOffers.filter { offer ->
                offer.coordinates.let { offerCoordinates ->
                    calculateDistance(
                        professionalCoordinates.latitude, professionalCoordinates.longitude,
                        offerCoordinates.latitude, offerCoordinates.longitude
                    ) <= professional.coverageRadius
                }
            }

            Timber.d("✅ Profesional ${professional.profileId} tiene ${offersWithinRadius.size} ofertas en su radio de ${professional.coverageRadius} km.")
            return@withContext Pair(professional, offersWithinRadius)
        }
    }

    private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val R = 6371
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLon / 2) * sin(dLon / 2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return R * c
    }
}
