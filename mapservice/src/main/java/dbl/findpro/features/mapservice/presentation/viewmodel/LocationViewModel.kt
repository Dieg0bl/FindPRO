package dbl.findpro.features.mapservice.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dbl.findpro.core.domain.model.userGroupsAndProfiles.Coordinates
import dbl.findpro.core.domain.usecase.GetUserLocationUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val getUserLocationUseCase: GetUserLocationUseCase
) : ViewModel() {

    private val _userCoordinates = MutableStateFlow<Coordinates?>(null)
    val userCoordinates: StateFlow<Coordinates?> get() = _userCoordinates

    fun hasLocationPermission(): Boolean {
        return getUserLocationUseCase.hasLocationPermission()
    }

    fun loadUserLocation(userAddress: String? = null) {
        viewModelScope.launch {
            val coordinates = getUserLocationUseCase(userAddress)
            _userCoordinates.value = coordinates
            Timber.d("📍 Ubicación actualizada: $coordinates")
        }
    }
}
