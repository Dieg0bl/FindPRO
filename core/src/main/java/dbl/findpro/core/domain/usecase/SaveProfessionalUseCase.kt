package dbl.findpro.core.domain.usecase

import dbl.findpro.core.domain.model.userGroupsAndProfiles.ProfessionalProfile
import dbl.findpro.core.domain.repository.IProfessionalProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveProfessionalUseCase @Inject constructor(
    private val repository: IProfessionalProfileRepository
) {
    suspend operator fun invoke(professional: ProfessionalProfile): Boolean {
        return withContext(Dispatchers.IO) {
            repository.saveProfessionalProfile(professional)
        }
    }
}
