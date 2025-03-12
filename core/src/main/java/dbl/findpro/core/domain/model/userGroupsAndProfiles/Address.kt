package dbl.findpro.core.domain.model.userGroupsAndProfiles

data class Address(
    val addressId: String,
    val street: String? = null,
    val city: String? = null,
    val province: String? = null,
    val autonomousCommunity: String? = null,
    val country: String? = null,
    val postalCode: String? = null
)
fun Address.toCoordinates(): Coordinates = Coordinates(
    latitude = 0.0, // 🔹 Se obtendrán en otra capa de la app (ej. geocodificación)
    longitude = 0.0
)

fun Coordinates.toAddress(): Address = Address(
    addressId = "", // 🔹 Se debe asignar desde Firestore
    street = null,
    city = null,
    province = null,
    autonomousCommunity = null,
    country = null,
    postalCode = null
)