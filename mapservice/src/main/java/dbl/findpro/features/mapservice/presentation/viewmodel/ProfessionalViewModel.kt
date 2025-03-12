package dbl.findpro.features.mapservice.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dbl.findpro.core.domain.model.userGroupsAndProfiles.Coordinates
import dbl.findpro.core.domain.model.userGroupsAndProfiles.Offer
import dbl.findpro.core.domain.model.userGroupsAndProfiles.ProfessionalProfile
import dbl.findpro.core.domain.usecase.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfessionalViewModel @Inject constructor(
    private val getProfessionalsUseCase: GetProfessionalsUseCase,
    private val saveProfessionalUseCase: SaveProfessionalUseCase,
    private val deleteProfessionalUseCase: DeleteProfessionalUseCase,
    private val filterProfessionalsByCategoryUseCase: FilterProfessionalsByCategoryUseCase,
    private val getProfessionalWithOffersUseCase: GetProfessionalWithOffersUseCase
) : ViewModel() {

    private val _userCoordinates = MutableStateFlow<Coordinates?>(null)
    val userCoordinates: StateFlow<Coordinates?> get() = _userCoordinates

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> get() = _selectedCategory

    private val _professionals = MutableStateFlow<List<ProfessionalProfile>>(emptyList())
    val professionals: StateFlow<List<ProfessionalProfile>> get() = _professionals

    private val _professional = MutableStateFlow<ProfessionalProfile?>(null)
    val professional: StateFlow<ProfessionalProfile?> get() = _professional

    private val _offersWithinRadius = MutableStateFlow<List<Offer>>(emptyList())
    val offersWithinRadius: StateFlow<List<Offer>> get() = _offersWithinRadius

    init {
        loadProfessionals()
    }

    /**
     * 🔹 **Carga la lista de profesionales**
     */
    fun loadProfessionals() {
        viewModelScope.launch {
            try {
                _professionals.value = getProfessionalsUseCase()
                Timber.d("✅ Profesionales cargados: ${_professionals.value.size}")
            } catch (e: Exception) {
                Timber.e(e, "❌ Error al obtener profesionales")
            }
        }
    }

    /**
     * 🔹 **Filtra profesionales por categoría**
     */
    suspend fun filterProfessionalsByCategory(categoryId: String): List<ProfessionalProfile> {
        return filterProfessionalsByCategoryUseCase(categoryId)
    }

    /**
     * 🔹 **Actualiza la ubicación del usuario**
     */
    fun updateUserLocation(coordinates: Coordinates?) {
        _userCoordinates.value = coordinates
        Timber.d("📍 Ubicación del usuario actualizada: ${_userCoordinates.value?.latitude}, ${_userCoordinates.value?.longitude}")
    }

    /**
     * 🔹 **Guarda un profesional en Firestore**
     */
    fun saveProfessional(professional: ProfessionalProfile) {
        viewModelScope.launch {
            try {
                val success = saveProfessionalUseCase(professional)
                if (success) loadProfessionals()
                Timber.d("✅ Profesional guardado correctamente")
            } catch (e: Exception) {
                Timber.e(e, "❌ Error al guardar el profesional")
            }
        }
    }

    /**
     * 🔹 **Elimina un profesional en Firestore**
     */
    fun deleteProfessional(profileId: String) {
        viewModelScope.launch {
            try {
                val success = deleteProfessionalUseCase(profileId)
                if (success) loadProfessionals()
                Timber.d("✅ Profesional eliminado correctamente")
            } catch (e: Exception) {
                Timber.e(e, "❌ Error al eliminar el profesional")
            }
        }
    }

    /**
     * 🔹 **Carga un profesional con sus ofertas dentro de su radio**
     */
    fun loadProfessionalWithOffers(profileId: String) {
        viewModelScope.launch {
            try {
                val (professional, offers) = getProfessionalWithOffersUseCase(profileId)
                _professional.value = professional
                _offersWithinRadius.value = offers
                Timber.d("✅ Cargado profesional ${professional?.profileId} con ${offers.size} ofertas en su radio")
            } catch (e: Exception) {
                Timber.e(e, "❌ Error al cargar el profesional y sus ofertas")
            }
        }
    }

    companion object {
        private val DEFAULT_USER_COORDINATES = Coordinates(42.8805, -8.5457)
    }
}
