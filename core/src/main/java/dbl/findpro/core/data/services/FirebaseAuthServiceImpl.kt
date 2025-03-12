package dbl.findpro.core.data.services

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import dbl.findpro.core.data.mappers.toDomainUser
import dbl.findpro.core.domain.model.userGroupsAndProfiles.User
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseAuthServiceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : IFirebaseAuthService {

    override suspend fun login(email: String, password: String): Result<Boolean> {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email.trim(), password).await()
            Result.success(true)
        } catch (e: Exception) {
            Timber.e(e, "❌ Firebase login error")
            Result.failure(e)
        }
    }

    override suspend fun forgotPassword(email: String): Result<Boolean> {
        return try {
            firebaseAuth.sendPasswordResetEmail(email.trim()).await()
            Result.success(true)
        } catch (e: Exception) {
            Timber.e(e, "❌ Error al enviar email de recuperación")
            Result.failure(e)
        }
    }

    override suspend fun register(email: String, password: String, name: String): Result<String?> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email.trim(), password).await()
            val user = result.user
            // Actualizar displayName si es necesario
            user?.updateProfile(
                UserProfileChangeRequest.Builder().setDisplayName(name).build()
            )?.await()
            Result.success(user?.uid)
        } catch (e: Exception) {
            Timber.e(e, "❌ Error en el registro de Firebase")
            Result.failure(e)
        }
    }

    override suspend fun googleSignUp(idToken: String): Result<String?> {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val result = firebaseAuth.signInWithCredential(credential).await()
            Result.success(result.user?.uid)
        } catch (e: Exception) {
            Timber.e(e, "❌ Error en Google Sign-Up")
            Result.failure(e)
        }
    }

    override suspend fun googleSignIn(idToken: String): Result<String?> {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val result = firebaseAuth.signInWithCredential(credential).await()
            Result.success(result.user?.uid)
        } catch (e: Exception) {
            Timber.e(e, "❌ Error en Google Sign-In")
            Result.failure(e)
        }
    }

    override suspend fun logout(): Result<Boolean> {
        return try {
            firebaseAuth.signOut()
            Result.success(true)
        } catch (e: Exception) {
            Timber.e(e, "❌ Error al cerrar sesión en Firebase")
            Result.failure(e)
        }
    }

    override suspend fun getCurrentUser(): Result<User?> {
        return try {
            val firebaseUser = firebaseAuth.currentUser
            val user = firebaseUser?.toDomainUser()  // Utiliza la función de extensión creada
            Result.success(user)
        } catch (e: Exception) {
            Timber.e(e, "❌ Error al obtener el usuario actual")
            Result.failure(e)
        }
    }



    override suspend fun getCurrentUserEmail(): Result<String?> {
        return try {
            Result.success(firebaseAuth.currentUser?.email)
        } catch (e: Exception) {
            Timber.e(e, "❌ Error al obtener el email del usuario actual")
            Result.failure(e)
        }
    }

    override suspend fun isEmailInUse(email: String): Result<Boolean> {
        // Firebase Auth no provee una verificación directa de uso de email.
        return Result.failure(UnsupportedOperationException("Verificación de email no implementada en FirebaseAuthService"))
    }

    override suspend fun isLoggedIn(): Result<Boolean> {
        return try {
            Result.success(firebaseAuth.currentUser != null)
        } catch (e: Exception) {
            Timber.e(e, "❌ Error al verificar sesión activa")
            Result.failure(e)
        }
    }

    override suspend fun getCurrentUserId(): Result<String?> {
        return try {
            val uid = firebaseAuth.currentUser?.uid
            Result.success(uid)
        } catch (e: Exception) {
            Timber.e(e, "❌ Error al obtener el user ID actual")
            Result.failure(e)
        }
    }


    override suspend fun deleteUser(userId: String): Result<Boolean> {
        return try {
            val currentUser = firebaseAuth.currentUser
            if (currentUser != null && currentUser.uid == userId) {
                currentUser.delete().await()
                Result.success(true)
            } else {
                Result.failure(Exception("Usuario no autenticado o ID no coincide"))
            }
        } catch (e: Exception) {
            Timber.e(e, "❌ Error al eliminar usuario en Firebase Auth")
            Result.failure(e)
        }
    }

    override suspend fun getGoogleIdToken(): Result<String?> {
        return try {
            val token = firebaseAuth.currentUser?.getIdToken(true)?.await()?.token
            Result.success(token)
        } catch (e: Exception) {
            Timber.e(e, "❌ Error al obtener Google ID Token")
            Result.failure(e)
        }
    }
}
