package dbl.findpro.authentication.domain.usecase

import dbl.findpro.core.domain.repository.IAuthenticationRepository
import timber.log.Timber
import javax.inject.Inject

class GoogleSignInUseCase @Inject constructor(
    private val authenticationRepository: IAuthenticationRepository
) {
    suspend operator fun invoke(): Result<String> {
        return try {
            val idToken = authenticationRepository.getGoogleIdToken()
            if (idToken != null) {
                authenticationRepository.googleSignIn(idToken)
            } else {
                Timber.e("❌ No se pudo obtener el ID Token para Google Sign-In")
                Result.failure(Exception("No se pudo obtener el ID Token"))
            }
        } catch (e: Exception) {
            Timber.e("❌ Error en GoogleSignInUseCase: ${e.message}")
            Result.failure(e)
        }
    }
}
