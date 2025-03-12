package dbl.findpro.features.mapservice.data.services

import dbl.findpro.features.mapservice.data.responseModel.DirectionsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IMapboxDirectionsService {
    @GET("directions/v5/mapbox/driving/{origin};{destination}")
    suspend fun getDirections(
        @Path("origin") origin: String, // Formato "lon,lat"
        @Path("destination") destination: String, // Formato "lon,lat"
        @Query("access_token") token: String
    ): Response<DirectionsResponse>
}
