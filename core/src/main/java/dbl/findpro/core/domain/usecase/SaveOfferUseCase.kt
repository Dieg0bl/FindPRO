package dbl.findpro.core.domain.usecase

import dbl.findpro.core.domain.model.userGroupsAndProfiles.Offer
import dbl.findpro.core.domain.repository.IOfferRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SaveOfferUseCase(private val repository: IOfferRepository) {
    suspend operator fun invoke(offer: Offer): Boolean {
        return withContext(Dispatchers.IO) {
            repository.saveOffer(offer)
        }
    }
}
