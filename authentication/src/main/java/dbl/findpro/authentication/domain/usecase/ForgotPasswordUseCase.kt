package dbl.findpro.authentication.domain.usecase

import dbl.findpro.core.domain.repository.IAuthenticationRepository
import timber.log.Timber
import javax.inject.Inject

class ForgotPasswordUseCase @Inject constructor(
    private val authenticationRepository: IAuthenticationRepository
) {
    suspend operator fun invoke(email: String): Result<Boolean> {
        return try {
            authenticationRepository.forgotPassword(email)
        } catch (e: Exception) {
            Timber.e(e, "❌ Error en ForgotPasswordUseCase")
            Result.failure(e)
        }
    }
}
