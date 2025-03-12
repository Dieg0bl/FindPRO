package dbl.findpro.userprofiles.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dbl.findpro.core.domain.model.userGroupsAndProfiles.ParticularProfile
import dbl.findpro.userprofiles.domain.usecase.SaveParticularProfileUseCase
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import java.util.UUID

@HiltViewModel
class ParticularProfileViewModel @Inject constructor(
    private val saveParticularProfileUseCase: SaveParticularProfileUseCase
) : ViewModel() {

    fun getInitialProfile(): ParticularProfile {
        return ParticularProfile(
            userId = "", // Se establecerá al obtener el usuario autenticado
            profileId = UUID.randomUUID().toString(), // Se genera un ID único
            address = null,
            coordinates = null,
            contactId = "",
            offerIdsList = emptyList()
        )
    }

    fun validateProfile(profile: ParticularProfile): Boolean {
        return profile.address != null && profile.contactId.isNotBlank()
    }

    fun saveProfile(profile: ParticularProfile) {
        viewModelScope.launch {
            try {
                saveParticularProfileUseCase(profile)
                Timber.d("✅ Perfil Particular activado con éxito: ${profile.profileId}")
            } catch (e: Exception) {
                Timber.e("❌ Error al guardar el perfil: ${e.message}")
            }
        }
    }
}
