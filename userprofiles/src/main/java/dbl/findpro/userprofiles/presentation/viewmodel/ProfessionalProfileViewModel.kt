package dbl.findpro.userprofiles.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dbl.findpro.core.domain.model.userGroupsAndProfiles.Category
import dbl.findpro.core.domain.model.userGroupsAndProfiles.Coordinates
import dbl.findpro.core.domain.model.userGroupsAndProfiles.ProfessionalProfile
import dbl.findpro.core.domain.repository.ICategoryRepository
import dbl.findpro.userprofiles.domain.usecase.SaveProfessionalProfileUseCase
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ProfessionalProfileViewModel @Inject constructor(
    private val saveProfessionalProfileUseCase: SaveProfessionalProfileUseCase,
    private val categoryRepository: ICategoryRepository // ✅ Se inyecta el repositorio de categorías
) : ViewModel() {

    val categories = categoryRepository.getAllCategories() // ✅ Se obtiene la lista de categorías

    fun getInitialProfile(): ProfessionalProfile {
        return ProfessionalProfile(
            userId = "",
            profileId = UUID.randomUUID().toString(),
            profileProPicture = null,
            performanceIndicatorsId = null,
            category = categories.firstOrNull() ?: Category( // ✅ Se usa la primera categoría o una por defecto
                categoryId = "default",
                name = "Sin categoría",
                icon = dbl.findpro.core.R.drawable.ic_ubicacion
            ),
            reviewIdsList = null,
            address = null,
            coordinates = Coordinates(0.0, 0.0),
            contactId = "",
            coverageRadius = 10,
            calendarId = null,
            applicationIdsList = emptyList()
        )
    }

    fun validateProfile(profile: ProfessionalProfile): Boolean {
        return profile.address != null && profile.contactId.isNotBlank()
    }

    fun saveProfile(profile: ProfessionalProfile) {
        viewModelScope.launch {
            try {
                saveProfessionalProfileUseCase(profile)
                Timber.d("✅ Perfil Profesional activado con éxito: ${profile.profileId}")
            } catch (e: Exception) {
                Timber.e("❌ Error al guardar el perfil: ${e.message}")
            }
        }
    }
}
