package dbl.findpro.authentication.domain.usecase

import dbl.findpro.core.domain.model.userGroupsAndProfiles.User
import dbl.findpro.core.domain.repository.IAuthenticationRepository
import dbl.findpro.core.domain.repository.IUserRepository
import timber.log.Timber
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authenticationRepository: IAuthenticationRepository,
    private val userRepository: IUserRepository
) {
    suspend operator fun invoke(user: User, password: String): Result<String> {
        Timber.d("📩 Iniciando registro para el usuario: ${user.email}")

        val authResult = authenticationRepository.register(user.email, password)
        if (authResult.isFailure) return authResult

        val userId = authResult.getOrNull() ?: return Result.failure(Exception("UID no obtenido"))

        val newUser = user.copy(userId = userId)
        val firestoreSuccess = userRepository.createUser(newUser)

        if (!firestoreSuccess) {
            userRepository.deleteUserById(userId)
            return Result.failure(Exception("Error al crear el usuario en Firestore, revertido en Auth"))
        }

        return Result.success(userId)
    }
}
