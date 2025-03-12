package dbl.findpro.core.domain.repository

import dbl.findpro.core.domain.model.userGroupsAndProfiles.User
import kotlinx.coroutines.flow.Flow

interface IAuthenticationRepository {
    suspend fun login(email: String, password: String): Boolean
    suspend fun forgotPassword(email: String): Result<Boolean>
    suspend fun register(email: String, password: String): Result<String>
    suspend fun googleSignUp(idToken: String): Result<String>
    suspend fun googleSignIn(idToken: String): Result<String>
    suspend fun logout(): Boolean
    suspend fun isLoggedIn(): Boolean
    suspend fun getCurrentUser(): Result<User?>
    suspend fun getCurrentUserEmail(): String?
    suspend fun isEmailInUse(email: String): Boolean
    suspend fun getGoogleIdToken(): String?
    suspend fun deleteUserById(userId: String): Boolean
}
