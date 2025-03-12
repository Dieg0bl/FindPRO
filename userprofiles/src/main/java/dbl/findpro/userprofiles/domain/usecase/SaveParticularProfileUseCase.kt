package dbl.findpro.userprofiles.domain.usecase

import dbl.findpro.core.domain.model.userGroupsAndProfiles.ParticularProfile
import dbl.findpro.core.domain.repository.IParticularProfileRepository
import timber.log.Timber
import javax.inject.Inject

class SaveParticularProfileUseCase @Inject constructor(
    private val particularProfileRepository: IParticularProfileRepository
) {
    suspend operator fun invoke(profile: ParticularProfile) {
        if (profile.userId.isBlank() || profile.profileId.isBlank()) {
            Timber.e("❌ No se puede guardar un perfil sin ID de usuario o perfil.")
            throw IllegalArgumentException("El perfil debe tener un ID de usuario y un ID de perfil válidos.")
        }

        if (profile.address == null || profile.contactId.isBlank()) {
            Timber.e("❌ Falta información obligatoria en el perfil de particular.")
            throw IllegalArgumentException("El perfil de particular debe incluir una dirección y un contacto válido.")
        }

        particularProfileRepository.saveParticularProfile(profile)
        Timber.d("✅ Perfil Particular guardado correctamente: ${profile.profileId}")
    }
}
