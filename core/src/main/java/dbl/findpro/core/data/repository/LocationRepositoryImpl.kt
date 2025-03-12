package dbl.findpro.core.data.repository

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
import dbl.findpro.core.domain.model.userGroupsAndProfiles.Coordinates
import dbl.findpro.core.domain.repository.IGeocodingRepository
import dbl.findpro.core.domain.repository.ILocationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepositoryImpl @Inject constructor(
    private val context: Context,
    private val IGeocodingRepository: IGeocodingRepository // ✅ Solo obtiene coordenadas de direcciones
) : ILocationRepository {

    override fun hasLocationPermission(): Boolean {
        val hasFineLocationPermission = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val hasCoarseLocationPermission = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val granted = hasFineLocationPermission || hasCoarseLocationPermission
        Timber.d("🔍 Verificación de permisos de ubicación: $granted")
        return granted
    }

    @SuppressLint("MissingPermission")
    override suspend fun getDeviceLocation(): Coordinates? = withContext(Dispatchers.IO) {
        if (!hasLocationPermission()) {
            Timber.e("🚨 Permisos de ubicación no concedidos.")
            return@withContext null
        }

        try {
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            var location: Location? = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                ?: locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

            if (location == null) {
                Timber.w("⚠️ Ubicación nula, reintentando en 2 segundos...")
                delay(2000)
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    ?: locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            }

            location?.let {
                Timber.d("📍 Ubicación obtenida: ${it.latitude}, ${it.longitude}")
                Coordinates(it.latitude, it.longitude)
            }
        } catch (e: Exception) {
            Timber.e(e, "❌ Error al obtener la ubicación")
            null
        }
    }

    override suspend fun getBestAvailableLocation(userAddress: String?): Coordinates {
        return withContext(Dispatchers.IO) {
            getDeviceLocation() ?: IGeocodingRepository.geocode(userAddress ?: "") ?: Coordinates(40.4168, -3.7038)
        }
    }
}
