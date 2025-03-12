package dbl.findpro.core.data.mappers

import dbl.findpro.core.domain.model.userGroupsAndProfiles.Address

// 📌 Convierte una `Address` a `Map<String, Any>`
fun Address.toMap(): Map<String, Any> = mapOf(
    "addressId" to addressId,
    "street" to (street ?: ""),
    "city" to (city ?: ""),
    "province" to (province ?: ""),
    "autonomousCommunity" to (autonomousCommunity ?: ""),
    "country" to (country ?: ""),
    "postalCode" to (postalCode ?: "")
)

// 📌 Convierte un `Map<String, Any>` de Firestore a `Address` con Conversión Segura
fun Map<*, *>.toAddress(): Address {
    val safeMap = this.mapKeys { it.key.toString() } // 🔹 Convierte claves a String de forma segura
    return Address(
        addressId = safeMap["addressId"] as? String ?: "",
        street = safeMap["street"] as? String,
        city = safeMap["city"] as? String,
        province = safeMap["province"] as? String,
        autonomousCommunity = safeMap["autonomousCommunity"] as? String,
        country = safeMap["country"] as? String,
        postalCode = safeMap["postalCode"] as? String
    )
}
