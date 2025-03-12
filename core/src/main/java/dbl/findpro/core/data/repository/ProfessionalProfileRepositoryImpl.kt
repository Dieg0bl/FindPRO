package dbl.findpro.core.data.repository

import dbl.findpro.core.data.mappers.toMap
import dbl.findpro.core.data.mappers.toProfessionalProfile
import dbl.findpro.core.data.services.IFirestoreService
import dbl.findpro.core.domain.model.userGroupsAndProfiles.ProfessionalProfile
import dbl.findpro.core.domain.repository.ICategoryRepository
import dbl.findpro.core.domain.repository.IProfessionalProfileRepository
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfessionalProfileRepositoryImpl @Inject constructor(
    private val firestoreService: IFirestoreService,
    private val categoryRepository: ICategoryRepository
) : IProfessionalProfileRepository {

    override suspend fun getAllProfessionals(): List<ProfessionalProfile> {
        return try {
            val categoryMap = categoryRepository.getAllCategories().associateBy { it.categoryId }

            firestoreService.getAllDocuments("professionals")
                .getOrNull()
                ?.mapNotNull {
                    val categoryId = it["categoryId"] as? String
                    val category = categoryId?.let { categoryMap[it] } ?: return@mapNotNull null
                    it.toProfessionalProfile(category)
                }
                ?: emptyList()
        } catch (e: Exception) {
            Timber.e(e, "❌ Error al obtener profesionales de Firestore")
            emptyList()
        }
    }

    override suspend fun saveProfessionalProfile(professional: ProfessionalProfile): Boolean {
        return try {
            firestoreService.saveData("professionals", professional.profileId, professional.toMap()).isSuccess
        } catch (e: Exception) {
            Timber.e(e, "❌ Error al guardar el profesional en Firestore")
            false
        }
    }

    override suspend fun getProfessionalByUserId(userId: String): ProfessionalProfile? {
        return try {
            val document = firestoreService.getDocument("professionals", userId).getOrNull()

            document?.let {
                val categoryId = it["categoryId"] as? String ?: return@let null
                val category = categoryRepository.getCategoryById(categoryId) ?: return@let null
                it.toProfessionalProfile(category) // ✅ Pasamos la categoría obtenida
            }
        } catch (e: Exception) {
            Timber.e(e, "❌ Error al obtener el perfil profesional por userId: $userId")
            null
        }
    }

    override suspend fun deleteProfessional(profileId: String): Boolean {
        return try {
            firestoreService.deleteDocument("professionals", profileId).isSuccess
        } catch (e: Exception) {
            Timber.e(e, "❌ Error al eliminar el profesional")
            false
        }
    }
}
