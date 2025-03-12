package dbl.findpro.authentication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dbl.findpro.authentication.domain.usecase.ForgotPasswordUseCase
import dbl.findpro.authentication.domain.usecase.GetGoogleIdTokenUseCase
import dbl.findpro.authentication.domain.usecase.GoogleSignInUseCase
import dbl.findpro.authentication.domain.usecase.GoogleSignUpUseCase
import dbl.findpro.authentication.domain.usecase.LoginUseCase
import dbl.findpro.authentication.domain.usecase.LogoutUseCase
import dbl.findpro.authentication.domain.usecase.RegisterUseCase
import dbl.findpro.authentication.utils.ValidationUtils
import dbl.findpro.core.domain.model.userGroupsAndProfiles.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val loginUseCase: LoginUseCase,
    private val forgotPasswordUseCase: ForgotPasswordUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val googleSignInUseCase: GoogleSignInUseCase,
    private val googleSignUpUseCase: GoogleSignUpUseCase,
    private val getGoogleIdTokenUseCase: GetGoogleIdTokenUseCase
) : ViewModel() {

    /**
     * Inicia sesión con email y contraseña.
     */
    suspend fun login(email: String, password: String): Result<Boolean> = withContext(Dispatchers.IO) {
        if (!ValidationUtils.isValidEmail(email)) {
            return@withContext Result.failure(Exception("Introduce un correo electrónico válido."))
        }
        if (password.isBlank()) {
            return@withContext Result.failure(Exception("La contraseña no puede estar vacía."))
        }

        val result = loginUseCase(email.trim(), password)
        if (result.isSuccess) {
            Timber.i("✅ Login exitoso para el usuario '$email'")
        } else {
            Timber.e("❌ Error en el login: ${result.exceptionOrNull()?.message}")
        }
        result
    }

    /**
     * Registra un nuevo usuario de forma transaccional.
     * - Crea el usuario en Firebase Auth.
     * - Si tiene éxito, lo guarda en Firestore.
     * - Si falla Firestore, elimina el usuario en Auth.
     */
    suspend fun register(user: User, password: String): Result<String> {
        return withContext(Dispatchers.IO) {
            registerUseCase(user, password)
        }
    }

    /**
     * Inicia sesión con Google.
     */
    suspend fun googleSignIn(): Result<String> = withContext(Dispatchers.IO) {
        val idToken = getGoogleIdTokenUseCase()
        if (idToken == null) {
            Timber.e("❌ No se pudo obtener el ID Token de Google.")
            return@withContext Result.failure(Exception("No se pudo obtener el ID Token de Google."))
        }

        val result = googleSignInUseCase()
        if (result.isSuccess) {
            Timber.i("✅ Inicio de sesión con Google exitoso.")
        } else {
            Timber.e("❌ Error en el inicio de sesión con Google: ${result.exceptionOrNull()?.message}")
        }
        result
    }

    /**
     * Registra un nuevo usuario con Google.
     */
    suspend fun googleSignUp(): Result<String> = withContext(Dispatchers.IO) {
        val idToken = getGoogleIdTokenUseCase()
        if (idToken == null) {
            Timber.e("❌ No se pudo obtener el ID Token de Google.")
            return@withContext Result.failure(Exception("No se pudo obtener el ID Token de Google."))
        }

        val result = googleSignUpUseCase()
        if (result.isSuccess) {
            Timber.i("✅ Registro con Google exitoso.")
        } else {
            Timber.e("❌ Error en el registro con Google: ${result.exceptionOrNull()?.message}")
        }
        result
    }

    /**
     * Inicia el proceso de recuperación de contraseña.
     */
    fun forgotPassword(email: String, onResult: (Result<Boolean>) -> Unit) {
        viewModelScope.launch {
            val result = forgotPasswordUseCase(email)
            if (result.isSuccess) {
                Timber.i("✅ Envío de correo para restablecimiento de contraseña a '$email'")
            } else {
                Timber.e("❌ Error al enviar correo de recuperación: ${result.exceptionOrNull()?.message}")
            }
            onResult(result)
        }
    }

    /**
     * Cierra sesión del usuario actual.
     */
    suspend fun logout(): Result<Boolean> = withContext(Dispatchers.IO) {
        val result = logoutUseCase()
        if (result.isSuccess) {
            Timber.i("✅ Cierre de sesión exitoso.")
        } else {
            Timber.e("❌ Error al cerrar sesión: ${result.exceptionOrNull()?.message}")
        }
        result
    }
}
