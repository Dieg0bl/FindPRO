package dbl.findpro.core.domain.repository

import dbl.findpro.core.domain.model.userGroupsAndProfiles.ProfileType
import dbl.findpro.core.domain.model.userGroupsAndProfiles.User

interface IUserRepository {
    suspend fun getUserById(userId: String): User?
    suspend fun createUser(user: User): Boolean
    suspend fun deleteUserById(userId: String): Boolean
    suspend fun updateUser(user: User): Boolean
    suspend fun setProfileType(userId: String, profileType: ProfileType): Boolean
    suspend fun updateProfileTypeInUse(userId: String, type: ProfileType)
}
