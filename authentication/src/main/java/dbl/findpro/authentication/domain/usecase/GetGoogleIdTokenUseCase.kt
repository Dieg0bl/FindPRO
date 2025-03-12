package dbl.findpro.authentication.domain.usecase

import dbl.findpro.core.domain.repository.IAuthenticationRepository
import timber.log.Timber
import javax.inject.Inject

class GetGoogleIdTokenUseCase @Inject constructor(
    private val authenticationRepository: IAuthenticationRepository
) {
    suspend operator fun invoke(): String? {
        return try {
            authenticationRepository.getGoogleIdToken()
        } catch (e: Exception) {
            Timber.e("❌ Error en GetGoogleIdTokenUseCase: ${e.message}")
            null
        }
    }
}
