package dbl.findpro.features.mapservice.data.responseModel

data class DirectionsResponse(
    val routes: List<Route>
)

data class Route(
    val distance: Double, // Distancia en metros
    val duration: Double, // Duración en segundos
    val geometry: String // Codificado en formato Polyline
)
