package dbl.findpro.core.domain.model.userGroupsAndProfiles

import java.time.Instant

sealed interface UserProfile {
    val userId: String     // ID del usuario dueño del perfil
    val profileId: String  // ID único del perfil
    val profileType: ProfileType
}
