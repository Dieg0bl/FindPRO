package dbl.findpro.features.mapservice.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dbl.findpro.core.domain.model.userGroupsAndProfiles.Offer
import dbl.findpro.core.domain.usecase.DeleteOfferUseCase
import dbl.findpro.core.domain.usecase.FilterOffersByCategoryUseCase
import dbl.findpro.core.domain.usecase.GetOffersUseCase
import dbl.findpro.core.domain.usecase.SaveOfferUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OfferViewModel @Inject constructor(
    private val getOffersUseCase: GetOffersUseCase,
    private val saveOfferUseCase: SaveOfferUseCase,
    private val deleteOfferUseCase: DeleteOfferUseCase,
    private val filterOffersByCategoryUseCase: FilterOffersByCategoryUseCase
) : ViewModel() {

    private val _offers = MutableStateFlow<List<Offer>>(emptyList())
    val offers: StateFlow<List<Offer>> get() = _offers

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> get() = _selectedCategory

    init {
        loadOffers()
    }

    fun loadOffers() {
        viewModelScope.launch {
            try {
                _offers.value = getOffersUseCase()
            } catch (e: Exception) {
                Timber.e(e, "❌ Error al cargar las ofertas")
            }
        }
    }

    suspend fun filterOffersByCategory(categoryId: String): List<Offer> {
        return filterOffersByCategoryUseCase(categoryId)
    }

    fun saveOffer(offer: Offer) {
        viewModelScope.launch {
            try {
                val success = saveOfferUseCase(offer)
                if (success) loadOffers()
            } catch (e: Exception) {
                Timber.e(e, "❌ Error al guardar la oferta")
            }
        }
    }

    fun deleteOffer(offerId: String) {
        viewModelScope.launch {
            try {
                val success = deleteOfferUseCase(offerId)
                if (success) loadOffers()
            } catch (e: Exception) {
                Timber.e(e, "❌ Error al eliminar la oferta")
            }
        }
    }
}
