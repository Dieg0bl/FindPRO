package dbl.findpro.core.domain.usecase

import dbl.findpro.core.domain.model.userGroupsAndProfiles.ProfessionalProfile
import dbl.findpro.core.domain.repository.IProfessionalProfileRepository
import javax.inject.Inject

class FilterProfessionalsByCategoryUseCase @Inject constructor(
    private val IProfessionalProfileRepository: IProfessionalProfileRepository
) {
    suspend operator fun invoke(categoryId: String): List<ProfessionalProfile> {
        return IProfessionalProfileRepository.getAllProfessionals().filter { it.category.categoryId == categoryId }
    }
}
