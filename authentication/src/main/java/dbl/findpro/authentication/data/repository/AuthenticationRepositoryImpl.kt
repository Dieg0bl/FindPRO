package dbl.findpro.authentication.data.repository


import dbl.findpro.core.data.services.IFirebaseAuthService
import dbl.findpro.core.domain.model.userGroupsAndProfiles.User
import dbl.findpro.core.domain.repository.IAuthenticationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationRepositoryImpl @Inject constructor(
    private val firebaseAuthService: IFirebaseAuthService
) : IAuthenticationRepository {

    override suspend fun login(email: String, password: String): Boolean {
        return firebaseAuthService.login(email, password).getOrElse {
            Timber.e("❌ Error en login: ${it.message}")
            false
        }
    }

    override suspend fun forgotPassword(email: String): Result<Boolean> {
        return firebaseAuthService.forgotPassword(email).mapCatching { it }
    }

    override suspend fun register(email: String, password: String): Result<String> {
        return firebaseAuthService.register(email, password, name = "")
            .mapCatching { uid ->
                uid ?: throw Exception("Error en el registro: User ID nulo")
            }
    }

    override suspend fun googleSignUp(idToken: String): Result<String> {
        return firebaseAuthService.googleSignUp(idToken).mapCatching { uid ->
            uid ?: throw Exception("Error en Google Sign-Up: User ID nulo")
        }
    }

    override suspend fun googleSignIn(idToken: String): Result<String> {
        return firebaseAuthService.googleSignIn(idToken).mapCatching { uid ->
            uid ?: throw Exception("Error en Google Sign-In: User ID nulo")
        }
    }

    override suspend fun logout(): Boolean {
        return firebaseAuthService.logout().getOrElse {
            Timber.e("❌ Error en logout: ${it.message}")
            false
        }
    }

    override suspend fun getCurrentUser(): Result<User?> {
        return try {
            val firebaseUser = firebaseAuthService.getCurrentUser().getOrNull()
            Result.success(firebaseUser)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    override suspend fun getCurrentUserEmail(): String? {
        return firebaseAuthService.getCurrentUserEmail().getOrNull().also {
            if (it == null) Timber.e("❌ Error al obtener el email del usuario actual")
        }
    }

    override suspend fun isEmailInUse(email: String): Boolean {
        return firebaseAuthService.isEmailInUse(email).getOrElse {
            Timber.e("❌ Error al verificar si el email está en uso: ${it.message}")
            false
        }
    }

    override suspend fun isLoggedIn(): Boolean {
        return firebaseAuthService.isLoggedIn().getOrElse {
            Timber.e("❌ Error al verificar si el usuario está autenticado: ${it.message}")
            false
        }
    }

    override suspend fun deleteUserById(userId: String): Boolean {
        return try {
            firebaseAuthService.deleteUser(userId).getOrElse {
                Timber.e("❌ Error al eliminar usuario por ID: $userId - ${it.message}")
                false
            }
            Timber.wtf("✅ Usuario eliminado correctamente de Firebase Auth: $userId")
            true
        } catch (e: Exception) {
            Timber.e(e, "❌ Error inesperado al eliminar usuario con ID: $userId")
            false
        }
    }

    override suspend fun getGoogleIdToken(): String? {
        return firebaseAuthService.getGoogleIdToken().getOrNull().also {
            if (it == null) Timber.e("❌ Error al obtener el ID Token de Google")
        }
    }
}
