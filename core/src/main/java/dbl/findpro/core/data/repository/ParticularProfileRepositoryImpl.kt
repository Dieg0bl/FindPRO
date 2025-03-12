package dbl.findpro.core.data.repository

import dbl.findpro.core.data.mappers.toMap
import dbl.findpro.core.data.mappers.toParticularProfile
import dbl.findpro.core.data.services.IFirestoreService
import dbl.findpro.core.domain.model.userGroupsAndProfiles.ParticularProfile
import dbl.findpro.core.domain.repository.IParticularProfileRepository
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ParticularProfileRepositoryImpl @Inject constructor(
    private val firestoreService: IFirestoreService
) : IParticularProfileRepository {

    override suspend fun getAllParticulars(): List<ParticularProfile> {
        return try {
            firestoreService.getAllDocuments("particulars")
                .getOrNull()
                ?.mapNotNull { it.toParticularProfile() }
                ?: emptyList()
        } catch (e: Exception) {
            Timber.e(e, "❌ Error al obtener particulares de Firestore")
            emptyList()
        }
    }

    override suspend fun saveParticularProfile(particular: ParticularProfile): Boolean {
        return try {
            firestoreService.saveData("particulars", particular.profileId, particular.toMap()).isSuccess
        } catch (e: Exception) {
            Timber.e(e, "❌ Error al guardar el perfil particular en Firestore")
            false
        }
    }

    override suspend fun getParticularByUserId(userId: String): ParticularProfile? {
        return try {
            val document = firestoreService.getDocument("particulars", userId).getOrNull()
            document?.toParticularProfile()
        } catch (e: Exception) {
            Timber.e(e, "❌ Error al obtener el perfil particular por userId: $userId")
            null
        }
    }

    override suspend fun deleteParticular(profileId: String): Boolean {
        return try {
            firestoreService.deleteDocument("particulars", profileId).isSuccess
        } catch (e: Exception) {
            Timber.e(e, "❌ Error al eliminar el perfil particular")
            false
        }
    }
}
