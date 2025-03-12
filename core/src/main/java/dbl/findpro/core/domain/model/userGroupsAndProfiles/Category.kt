package dbl.findpro.core.domain.model.userGroupsAndProfiles

data class Category(
    val categoryId: String, // ID único
    val name: String,
    val icon: Int // 🔹 Se usará un recurso drawable (R.drawable.ic_category)
)