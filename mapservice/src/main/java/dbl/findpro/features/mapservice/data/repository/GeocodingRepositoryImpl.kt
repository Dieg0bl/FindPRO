package dbl.findpro.features.mapservice.data.repository

import dbl.findpro.core.domain.model.userGroupsAndProfiles.Address
import dbl.findpro.core.domain.model.userGroupsAndProfiles.Coordinates
import dbl.findpro.core.domain.repository.IGeocodingRepository
import dbl.findpro.core.util.RemoteConfigManager
import dbl.findpro.features.mapservice.data.services.IMapboxGeocodingService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class GeocodingRepositoryImpl @Inject constructor(
    private val IMapboxGeocodingService: IMapboxGeocodingService,
    private val remoteConfigManager: RemoteConfigManager
) : IGeocodingRepository {

    override suspend fun geocode(address: String): Coordinates? {
        return withContext(Dispatchers.IO) {
            try {
                val token = remoteConfigManager.fetchPrivateMapboxToken()
                val response = IMapboxGeocodingService.geocode(address, token)

                if (response.isSuccessful) {
                    response.body()?.features?.firstOrNull()?.geometry?.coordinates?.let {
                        Coordinates(it[1], it[0])
                    }
                } else null
            } catch (e: Exception) {
                Timber.e(e, "❌ Error en la geocodificación")
                null
            }
        }
    }

    override suspend fun reverseGeocode(coordinates: Coordinates): Address? {
        return withContext(Dispatchers.IO) {
            try {
                val token = remoteConfigManager.fetchPrivateMapboxToken()
                val response = IMapboxGeocodingService.reverseGeocode(coordinates.longitude, coordinates.latitude, token)

                response.body()?.features?.firstOrNull()?.let { feature ->
                    val contextList = feature.context ?: emptyList()

                    Address(
                        addressId = "",
                        street = feature.placeName,
                        city = contextList.find { it.id.startsWith("place") }?.text,
                        province = contextList.find { it.id.startsWith("region") }?.text,
                        autonomousCommunity = contextList.find { it.id.startsWith("district") }?.text,
                        country = contextList.find { it.id.startsWith("country") }?.text,
                        postalCode = contextList.find { it.id.startsWith("postcode") }?.text
                    )
                }
            } catch (e: Exception) {
                Timber.e(e, "❌ Error en la conversión de coordenadas a dirección")
                null
            }
        }
    }

    override suspend fun isInSpain(coordinates: Coordinates): Boolean {
        return withContext(Dispatchers.IO) {
            reverseGeocode(coordinates)?.country?.lowercase() == "spain"
        }
    }

    override suspend fun isValidLandLocation(coordinates: Coordinates): Boolean {
        return withContext(Dispatchers.IO) {
            reverseGeocode(coordinates) != null
        }
    }
}
