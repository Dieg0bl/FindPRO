package dbl.findpro.core.domain.usecase

import dbl.findpro.core.domain.repository.IProfessionalProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteProfessionalUseCase @Inject constructor(
    private val repository: IProfessionalProfileRepository
) {
    suspend operator fun invoke(professionalId: String): Boolean {
        return withContext(Dispatchers.IO) {
            repository.deleteProfessional(professionalId)
        }
    }
}
