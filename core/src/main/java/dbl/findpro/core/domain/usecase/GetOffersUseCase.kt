package dbl.findpro.core.domain.usecase

import dbl.findpro.core.domain.model.userGroupsAndProfiles.Offer
import dbl.findpro.core.domain.repository.IOfferRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetOffersUseCase(private val repository: IOfferRepository) {
    suspend operator fun invoke(): List<Offer> {
        return withContext(Dispatchers.IO) {
            repository.getAllOffers()
        }
    }
}
