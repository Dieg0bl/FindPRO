

package dbl.findpro.core.domain.repository

import dbl.findpro.core.domain.model.userGroupsAndProfiles.ProfessionalProfile

interface IProfessionalProfileRepository {
    suspend fun saveProfessionalProfile(professional: ProfessionalProfile): Boolean
    suspend fun getAllProfessionals(): List<ProfessionalProfile>
    suspend fun getProfessionalByUserId(userId: String): ProfessionalProfile?
    suspend fun deleteProfessional(string: String): Boolean
}
