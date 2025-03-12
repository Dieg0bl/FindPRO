package dbl.findpro.core.domain.usecase

import dbl.findpro.core.domain.model.userGroupsAndProfiles.Coordinates
import dbl.findpro.core.domain.repository.ILocationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetUserLocationUseCase @Inject constructor(
    private val ILocationRepository: ILocationRepository
) {
    suspend operator fun invoke(userAddress: String?): Coordinates {
        return withContext(Dispatchers.IO) {
            ILocationRepository.getBestAvailableLocation(userAddress)
        }
    }

    fun hasLocationPermission(): Boolean {
        return ILocationRepository.hasLocationPermission()
    }
}
