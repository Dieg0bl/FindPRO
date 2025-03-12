package dbl.findpro.authentication.domain.usecase

import dbl.findpro.core.domain.repository.IAuthenticationRepository
import timber.log.Timber
import javax.inject.Inject

class ValidateUserUseCase @Inject constructor(
    private val authenticationRepository: IAuthenticationRepository // ✅ Correcto, usa la interfaz
) {
    suspend operator fun invoke(email: String): Result<Boolean> {
        return try {
            Timber.d("Validando email: $email")
            val isValid = authenticationRepository.isEmailInUse(email)
            Timber.i("Resultado de validación para $email: $isValid")
            Result.success(isValid)
        } catch (e: Exception) {
            Timber.e(e, "Error validando email: $email")
            Result.failure(e)
        }
    }
}
