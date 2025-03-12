package dbl.findpro.core.domain.repository

import dbl.findpro.core.domain.model.userGroupsAndProfiles.Offer


interface IOfferRepository {
    suspend fun getAllOffers(): List<Offer>
    suspend fun saveOffer(offer: Offer): Boolean
    suspend fun deleteOffer(offerId: String): Boolean
}
