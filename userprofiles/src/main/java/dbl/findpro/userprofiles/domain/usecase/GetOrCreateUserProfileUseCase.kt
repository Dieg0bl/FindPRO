package dbl.findpro.userprofiles.domain.usecase

import dbl.findpro.core.domain.model.userGroupsAndProfiles.User
import dbl.findpro.core.domain.repository.IAuthenticationRepository
import kotlinx.coroutines.delay
import timber.log.Timber
import javax.inject.Inject

class GetOrCreateUserProfileUseCase @Inject constructor(
    private val authenticationRepository: IAuthenticationRepository
) {
    suspend operator fun invoke(): User? {
        repeat(5) { attempt ->
            // Obtiene el resultado del usuario actual (Result<User?>)
            val result = authenticationRepository.getCurrentUser()
            val currentUser = result.getOrNull()
            if (currentUser != null) {
                Timber.d("✅ Usuario obtenido: ${currentUser.userId}")
                return currentUser
            }
            Timber.w("⚠️ Usuario no disponible. Reintentando... (Intento ${attempt + 1}/5)")
            delay(1000) // Retraso de 1 segundo entre intentos
        }
        Timber.e("❌ No se pudo obtener el usuario tras varios intentos")
        return null
    }
}
