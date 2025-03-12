package dbl.findpro.datosPruebaDesarrollo

import dbl.findpro.core.domain.model.userGroupsAndProfiles.*
import dbl.findpro.core.domain.repository.ICategoryRepository
import java.time.Instant
import java.time.LocalDateTime
import kotlin.random.Random

object Information {

    fun generateMockUsers(): List<User> = List(10) {
        User(
            userId = "",
            name = listOf("Carlos", "Ana", "Luis", "Sofía", "David", "María", "Jorge", "Laura", "Pablo", "Elena").random(),
            email = listOf(
                "carlos@example.com", "ana@example.com", "luis@example.com", "sofia@example.com", "david@example.com",
                "maria@example.com", "jorge@example.com", "laura@example.com", "pablo@example.com", "elena@example.com"
            ).random()
        )
    }

    fun generateMockProfessionalProfiles(ICategoryRepository: ICategoryRepository): List<ProfessionalProfile> {
        val categoryMap = ICategoryRepository.getAllCategories().associateBy { it.categoryId } // ✅ Obtener `categoryMap`

        return List(10) {
            val randomCategory = categoryMap.values.random()

            ProfessionalProfile(
                userId = "",
                profileId = "",
                category = randomCategory, // ✅ Usamos el objeto `Category`
                address = null,
                contactId = "",
                coverageRadius = (10..50).random(),
                coordinates = Coordinates(
                    latitude = Random.nextDouble(36.0, 43.8),
                    longitude = Random.nextDouble(-9.5, 4.0)
                ),
                profileProPicture = null,
                performanceIndicatorsId = null,
                reviewIdsList = emptyList(),
                calendarId = null,
                applicationIdsList = emptyList()
            )
        }
    }

    fun generateMockParticularProfiles(users: List<User>): List<ParticularProfile> {
        return users.map { user ->
            ParticularProfile(
                userId = user.userId,
                profileId = "",
                address = null,
                contactId = "",
                offerIdsList = mutableListOf(),
                coordinates = Coordinates(
                    latitude = Random.nextDouble(36.0, 43.8),
                    longitude = Random.nextDouble(-9.5, 4.0)
                )
            )
        }
    }

    fun generateMockOffers(particulars: List<ParticularProfile>, ICategoryRepository: ICategoryRepository): List<Offer> {
        val categoryMap = ICategoryRepository.getAllCategories().associateBy { it.categoryId } // ✅ Obtener `categoryMap`

        return List(15) {
            val particular = particulars.random()
            val randomCategory = categoryMap.values.random()

            Offer(
                offerId = "",
                publisherId = particular.userId,
                category = randomCategory, // ✅ Usamos el objeto `Category`
                description = "Servicio requerido en esta zona",
                address = null,
                coordinates = Coordinates(
                    latitude = Random.nextDouble(36.0, 43.8),
                    longitude = Random.nextDouble(-9.5, 4.0)
                ),
                budget = Random.nextDouble(50.0, 1000.0),
                expectedPerformanceId = null,
                state = OfferState.ACTIVE,
                startDate = LocalDateTime.now(),
                endDate = LocalDateTime.now().plusDays((1..10).random().toLong()),
                urgency = Random.nextBoolean(),
                createdDate = Instant.now(),
                selectedCandidateId = null
            ).also {
                particular.offerIdsList = particular.offerIdsList + it.offerId
            }
        }
    }
}
