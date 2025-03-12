package dbl.findpro.core.data.mappers

import com.google.firebase.auth.FirebaseUser
import dbl.findpro.core.domain.model.userGroupsAndProfiles.ProfileType
import dbl.findpro.core.domain.model.userGroupsAndProfiles.User

fun User.toMap(): Map<String, Any> = mapOf(
    "userId" to userId,
    "name" to name,
    "email" to email,
    "preferencesId" to (preferencesId ?: ""),
    "profileTypeInUse" to profileTypeInUse.name
)

fun FirebaseUser.toDomainUser(): User {
    return User(
        userId = uid,
        name = displayName ?: "",
        email = email ?: "",
        preferencesId = null, // Si tienes este dato, puedes asignarlo; de lo contrario, null o un valor por defecto
        userGroupsIdsMap = emptyMap(), // Puedes mapearlo si tienes la información almacenada
        profileTypeInUse = ProfileType.UNDEFINED // O asignar otro valor si lo obtienes de alguna parte
    )
}

fun Map<String, Any>.toDomainUser(): User {
    return User(
        userId = this["userId"] as? String ?: "",
        name = this["name"] as? String ?: "",
        email = this["email"] as? String ?: "",
        preferencesId = this["preferencesId"] as? String,
        userGroupsIdsMap = emptyMap(), // Ajusta según lo que guardes
        profileTypeInUse = (this["profileTypeInUse"] as? String)?.let { ProfileType.valueOf(it) } ?: ProfileType.UNDEFINED
    )
}