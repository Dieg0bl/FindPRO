package dbl.findpro.core.domain.repository

import dbl.findpro.core.domain.model.userGroupsAndProfiles.ParticularProfile

interface IParticularProfileRepository {
    suspend fun saveParticularProfile(particular: ParticularProfile): Boolean
    suspend fun getAllParticulars(): List<ParticularProfile>
    suspend fun getParticularByUserId(userId: String): ParticularProfile?
    suspend fun deleteParticular(profileId: String): Boolean
}
