package dbl.findpro.core.domain.repository

import dbl.findpro.core.domain.model.userGroupsAndProfiles.Coordinates

interface ILocationRepository {

    /**
     * ✅ **Verifica si la aplicación tiene permisos de ubicación.**
     */
    fun hasLocationPermission(): Boolean

    /**
     * 📌 **Obtiene la última ubicación conocida del dispositivo (GPS o Red).**
     */
    suspend fun getDeviceLocation(): Coordinates?

    /**
     * ✅ **Obtiene la mejor ubicación disponible, considerando:**
     * - 📍 Ubicación del dispositivo si es válida.
     * - 🏙️ Dirección del usuario si el GPS falla.
     * - 🌍 Ubicación por defecto (Madrid) si todo falla.
     */
    suspend fun getBestAvailableLocation(userAddress: String?): Coordinates
}
