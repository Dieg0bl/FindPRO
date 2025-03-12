package dbl.findpro.core.domain.usecase

import dbl.findpro.core.domain.model.userGroupsAndProfiles.Offer
import dbl.findpro.core.domain.repository.IOfferRepository
import javax.inject.Inject

class FilterOffersByCategoryUseCase @Inject constructor(
    private val IOfferRepository: IOfferRepository
) {
    suspend operator fun invoke(categoryId: String): List<Offer> {
        return IOfferRepository.getAllOffers().filter { it.category.categoryId == categoryId }
    }
}
