package dbl.findpro.features.mapservice.data.services

import dbl.findpro.features.mapservice.data.responseModel.GeocodingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IMapboxGeocodingService {
    @GET("geocoding/v5/mapbox.places/{address}.json")
    suspend fun geocode(
        @Path("address") address: String,
        @Query("access_token") token: String
    ): Response<GeocodingResponse>

    @GET("geocoding/v5/mapbox.places/{longitude},{latitude}.json")
    suspend fun reverseGeocode(
        @Path("longitude") longitude: Double,
        @Path("latitude") latitude: Double,
        @Query("access_token") token: String
    ): Response<GeocodingResponse>
}
