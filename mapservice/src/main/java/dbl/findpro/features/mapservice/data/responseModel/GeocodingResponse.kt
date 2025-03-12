package dbl.findpro.features.mapservice.data.responseModel

import com.google.gson.annotations.SerializedName

data class GeocodingResponse(
    @SerializedName("features") val features: List<GeocodingFeature>
)

data class GeocodingFeature(
    @SerializedName("place_name") val placeName: String,
    @SerializedName("geometry") val geometry: Geometry,
    @SerializedName("context") val context: List<GeocodingContext>? = null
)

data class Geometry(
    @SerializedName("coordinates") val coordinates: List<Double>
)

data class GeocodingContext(
    @SerializedName("id") val id: String,
    @SerializedName("text") val text: String
)
