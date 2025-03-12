package dbl.findpro.authentication.domain.usecase

import dbl.findpro.core.domain.repository.IAuthenticationRepository
import timber.log.Timber
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authenticationRepository: IAuthenticationRepository
) {
    suspend operator fun invoke(): Result<Boolean> {
        return try {
            Timber.d("Attempting logout")
            val success = authenticationRepository.logout()
            if (success) {
                Timber.i("Logout successful")
                Result.success(true)
            } else {
                Timber.w("Logout failed")
                Result.failure(Exception("Logout failed"))
            }
        } catch (e: Exception) {
            Timber.e(e, "Error during logout")
            Result.failure(e)
        }
    }
}
