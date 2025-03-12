package dbl.findpro.authentication.domain.usecase

import dbl.findpro.core.domain.repository.IAuthenticationRepository
import timber.log.Timber
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authenticationRepository: IAuthenticationRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<Boolean> {
        return try {
            Timber.d("Attempting login for email: $email")
            val success = authenticationRepository.login(email, password)
            if (success) {
                Timber.i("Login successful for email: $email")
                Result.success(true)
            } else {
                Timber.w("Login failed for email: $email")
                Result.failure(Exception("Login failed"))
            }
        } catch (e: Exception) {
            Timber.e(e, "Error during login for email: $email")
            Result.failure(e)
        }
    }
}
