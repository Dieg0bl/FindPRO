package dbl.findpro.core.domain.model.userGroupsAndProfiles

data class User(
    val userId: String,
    val name: String,
    val email: String,
    val preferencesId: String? = null,
    val userGroupsIdsMap: Map<ProfileType, String> = emptyMap(),
    val profileTypeInUse: ProfileType = ProfileType.UNDEFINED
)
