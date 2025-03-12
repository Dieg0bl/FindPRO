package dbl.findpro.core.domain.model.userGroupsAndProfiles

data class ProfessionalProfile(
    override val userId: String,
    override val profileId: String,
    override val profileType: ProfileType = ProfileType.PROFESSIONAL,
    val profileProPicture: String? = null,
    val performanceIndicatorsId: String? = null,
    val category: Category,// 📌 Obligatorio
    val reviewIdsList: List<String>? = null,
    val address: Address?,        // 📌 Obligatorio
    val coordinates: Coordinates, // 📌 Ahora es un objeto directamente
    val contactId: String,        // 📌 Ahora es un ID, igual que en `ParticularProfile`
    val coverageRadius: Int = 0,  // 📏 En kilómetros
    val calendarId: String? = null,
    val applicationIdsList: List<String> = emptyList(),
) : UserProfile
