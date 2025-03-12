package dbl.findpro.userprofiles.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dbl.findpro.core.domain.model.userGroupsAndProfiles.ProfileType
import dbl.findpro.core.domain.model.userGroupsAndProfiles.User
import dbl.findpro.userprofiles.domain.usecase.GetOrCreateUserProfileUseCase
import dbl.findpro.userprofiles.domain.usecase.SetUserProfileTypeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SelectProfileViewModel @Inject constructor(
    private val getOrCreateUserProfileUseCase: GetOrCreateUserProfileUseCase,
    private val setUserProfileTypeUseCase: SetUserProfileTypeUseCase
) : ViewModel() {

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser = _currentUser.asStateFlow()

    init {
        viewModelScope.launch {
            Timber.d("🔍 Iniciando carga del usuario actual...")
            try {
                val user = getOrCreateUserProfileUseCase()
                if (user != null) {
                    _currentUser.value = user
                    Timber.i("✅ Usuario cargado correctamente: ${user.email}")
                } else {
                    Timber.e("❌ No se encontró un usuario autenticado en Firestore")
                    _currentUser.value = null
                }
            } catch (e: Exception) {
                Timber.e(e, "❌ Error al cargar el usuario")
                _currentUser.value = null
            }
        }
    }

    fun setProfileType(profileType: ProfileType, onResult: (Boolean) -> Unit) {
        val user = _currentUser.value
        if (user == null) {
            Timber.e("⚠️ No se puede establecer el perfil porque el usuario es nulo")
            onResult(false)
            return
        }

        viewModelScope.launch {
            val success = setUserProfileTypeUseCase(user.userId, profileType)
            onResult(success.isSuccess) // Aseguramos que el resultado sea un Boolean
            Timber.d(if (success.isSuccess) "✅ Perfil establecido correctamente" else "❌ Error al establecer el perfil")
        }
    }
}
