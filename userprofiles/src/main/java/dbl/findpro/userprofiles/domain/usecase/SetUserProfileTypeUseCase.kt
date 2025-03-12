package dbl.findpro.userprofiles.domain.usecase

import dbl.findpro.core.domain.model.userGroupsAndProfiles.ProfileType
import dbl.findpro.core.domain.repository.IUserRepository
import timber.log.Timber
import javax.inject.Inject

class SetUserProfileTypeUseCase @Inject constructor(
    private val userRepository: IUserRepository
) {
    suspend operator fun invoke(userId: String, profileType: ProfileType): Result<Unit> {
        return try {
            if (userId.isBlank()) {
                return Result.failure(Exception("❌ ID de usuario vacío."))
            }

            userRepository.updateProfileTypeInUse(userId, profileType)
            Timber.d("✅ Perfil actualizado a: $profileType para el usuario: $userId")
            Result.success(Unit)

        } catch (e: Exception) {
            Timber.e("❌ Error al actualizar el perfil en uso: ${e.message}")
            Result.failure(e)
        }
    }
}
