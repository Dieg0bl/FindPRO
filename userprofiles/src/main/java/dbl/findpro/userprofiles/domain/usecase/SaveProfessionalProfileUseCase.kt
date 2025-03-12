package dbl.findpro.userprofiles.domain.usecase

import dbl.findpro.core.domain.model.userGroupsAndProfiles.ProfessionalProfile
import dbl.findpro.core.domain.repository.IProfessionalProfileRepository
import timber.log.Timber
import javax.inject.Inject

class SaveProfessionalProfileUseCase @Inject constructor(
    private val professionalProfileRepository: IProfessionalProfileRepository
) {
    suspend operator fun invoke(profile: ProfessionalProfile) {
        if (profile.userId.isBlank() || profile.profileId.isBlank()) {
            Timber.e("❌ No se puede guardar un perfil sin ID de usuario o perfil.")
            throw IllegalArgumentException("El perfil debe tener un ID de usuario y un ID de perfil válidos.")
        }

        if (profile.address == null || profile.contactId.isBlank()) {
            Timber.e("❌ Falta información obligatoria en el perfil de profesional.")
            throw IllegalArgumentException("El perfil profesional debe incluir una categoría, dirección y contacto válido.")
        }

        professionalProfileRepository.saveProfessionalProfile(profile)
        Timber.d("✅ Perfil Profesional guardado correctamente: ${profile.profileId}")
    }
}
