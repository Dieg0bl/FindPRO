package dbl.findpro.core.data.services

import dbl.findpro.core.domain.model.userGroupsAndProfiles.User

interface IFirebaseAuthService {
    suspend fun login(email: String, password: String): Result<Boolean>
    suspend fun forgotPassword(email: String): Result<Boolean>
    suspend fun register(email: String, password: String, name: String): Result<String?>
    suspend fun logout(): Result<Boolean>
    suspend fun getCurrentUserEmail(): Result<String?>
    suspend fun isLoggedIn(): Result<Boolean>
    suspend fun getCurrentUserId(): Result<String?>
    suspend fun getCurrentUser(): Result<User?>
    suspend fun isEmailInUse(email: String): Result<Boolean>
    suspend fun deleteUser(userId: String): Result<Boolean>
    suspend fun googleSignIn(idToken: String): Result<String?>
    suspend fun getGoogleIdToken(): Result<String?>
    suspend fun googleSignUp(idToken: String): Result<String?>
}
