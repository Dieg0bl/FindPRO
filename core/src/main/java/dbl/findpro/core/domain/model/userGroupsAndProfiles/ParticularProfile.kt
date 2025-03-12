package dbl.findpro.core.domain.model.userGroupsAndProfiles

data class ParticularProfile(
    override val userId: String,
    override val profileId: String,
    override val profileType: ProfileType = ProfileType.PARTICULAR,
    val address: Address?,    // 📌 Mantiene la referencia a la dirección
    val coordinates: Coordinates? = null, // 📌 Opcional, útil si un particular tiene ubicación
    val contactId: String,    // 📌 Ahora es un ID que referencia los datos de contacto
    var offerIdsList: List<String> = emptyList(),
) : UserProfile