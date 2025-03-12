package dbl.findpro.core.domain.repository

import dbl.findpro.core.domain.model.userGroupsAndProfiles.Address
import dbl.findpro.core.domain.model.userGroupsAndProfiles.Coordinates

interface IGeocodingRepository {

    /**
     * 📌 **Convierte una dirección en coordenadas** usando servicios de geocodificación.
     *
     * @param address Dirección a convertir.
     * @return Coordenadas correspondientes o `null` si falla la conversión.
     */
    suspend fun geocode(address: String): Coordinates?

    /**
     * 📌 **Convierte coordenadas en una dirección** usando servicios de geocodificación inversa.
     *
     * @param coordinates Coordenadas a convertir.
     * @return Dirección correspondiente o `null` si falla la conversión.
     */
    suspend fun reverseGeocode(coordinates: Coordinates): Address?

    /**
     * 🌍 **Verifica si las coordenadas están dentro de España.**
     *
     * @param coordinates Coordenadas a validar.
     * @return `true` si están dentro de España, `false` en caso contrario.
     */
    suspend fun isInSpain(coordinates: Coordinates): Boolean

    /**
     * 🌊 **Verifica si una ubicación está en tierra firme (no en agua).**
     *
     * @param coordinates Coordenadas a validar.
     * @return `true` si está en tierra firme, `false` si parece estar en agua o zona no registrada.
     */
    suspend fun isValidLandLocation(coordinates: Coordinates): Boolean
}
