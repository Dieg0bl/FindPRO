package dbl.findpro.core.data.mappers

import dbl.findpro.core.domain.model.userGroupsAndProfiles.Category

// 📌 Convierte un `Category` a `Map<String, Any>`
fun Category.toMap(): Map<String, Any> = mapOf(
    "categoryId" to categoryId,
    "name" to name,
    "icon" to icon
)

// 📌 Convierte un `Map<String, Any>` de Firestore a `Category`
fun Map<String, Any>.toCategory(): Category = Category(
    categoryId = this["categoryId"] as? String ?: "",
    name = this["name"] as? String ?: "",
    icon = (this["icon"] as? Number)?.toInt() ?: 0
)
