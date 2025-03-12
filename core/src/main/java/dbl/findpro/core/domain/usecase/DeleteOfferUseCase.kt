package dbl.findpro.core.domain.usecase

import dbl.findpro.core.domain.repository.IOfferRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteOfferUseCase(private val repository: IOfferRepository) {
    suspend operator fun invoke(offerId: String): Boolean {
        return withContext(Dispatchers.IO) {
            repository.deleteOffer(offerId)
        }
    }
}
