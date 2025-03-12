package dbl.findpro.core.data.repository

import dbl.findpro.core.data.mappers.toMap
import dbl.findpro.core.data.mappers.toOffer
import dbl.findpro.core.data.services.IFirestoreService
import dbl.findpro.core.domain.model.userGroupsAndProfiles.Offer
import dbl.findpro.core.domain.repository.ICategoryRepository
import dbl.findpro.core.domain.repository.IOfferRepository
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OfferRepositoryImpl @Inject constructor(
    private val IFirestoreService: IFirestoreService,
    private val ICategoryRepository: ICategoryRepository
) : IOfferRepository {

    override suspend fun getAllOffers(): List<Offer> {
        return try {
            val categoryMap = ICategoryRepository.getAllCategories().associateBy { it.categoryId }

            IFirestoreService.getAllDocuments("offers")
                .getOrNull()
                ?.mapNotNull {
                    val categoryId = it["categoryId"] as? String
                    val category = categoryId?.let { categoryMap[it] } ?: return@mapNotNull null
                    it.toOffer(category)
                }
                ?: emptyList()
        } catch (e: Exception) {
            Timber.e(e, "❌ Error al obtener ofertas de Firestore")
            emptyList()
        }
    }

    override suspend fun saveOffer(offer: Offer): Boolean {
        return try {
            IFirestoreService.saveData("offers", offer.offerId, offer.toMap()).isSuccess
        } catch (e: Exception) {
            Timber.e(e, "❌ Error al guardar la oferta en Firestore")
            false
        }
    }

    override suspend fun deleteOffer(offerId: String): Boolean {
        return try {
            IFirestoreService.deleteDocument("offers", offerId).isSuccess
        } catch (e: Exception) {
            Timber.e(e, "❌ Error al eliminar la oferta")
            false
        }
    }
}
