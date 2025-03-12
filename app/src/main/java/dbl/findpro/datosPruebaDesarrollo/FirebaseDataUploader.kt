package dbl.findpro.datosPruebaDesarrollo

import dbl.findpro.core.data.services.IFirestoreService
import dbl.findpro.core.data.mappers.*
import dbl.findpro.core.domain.repository.ICategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class FirebaseDataUploader @Inject constructor(
    private val IFirestoreService: IFirestoreService,
    private val ICategoryRepository: ICategoryRepository
) {
    suspend fun uploadTestData() = withContext(Dispatchers.IO) {
        try {
            Timber.d("📤 Iniciando subida de datos simulados a Firestore...")

            val users = Information.generateMockUsers()
            val userIdMap = mutableMapOf<String, String>()

            // 🔹 1️⃣ Subir Usuarios a Firestore
            users.forEach { user ->
                val result = IFirestoreService.saveData("users", "", user.toMap())
                result.getOrNull()?.let { newId -> userIdMap[user.userId] = newId }
            }

            val particulars = Information.generateMockParticularProfiles(users)
            val particularIdMap = mutableMapOf<String, String>()

            // 🔹 2️⃣ Subir Perfiles Particulares con IDs reales
            particulars.forEach { particular ->
                val updatedParticular = particular.copy(userId = userIdMap[particular.userId] ?: particular.userId)
                val result = IFirestoreService.saveData("particulars", "", updatedParticular.toMap())
                result.getOrNull()?.let { newId -> particularIdMap[particular.profileId] = newId }
            }

            // 🔹 3️⃣ Generar Perfiles Profesionales usando `ICategoryRepository`
            val professionals = Information.generateMockProfessionalProfiles(ICategoryRepository) // ✅ Pasamos `ICategoryRepository`

            val professionalIdMap = mutableMapOf<String, String>()

            // 🔹 4️⃣ Subir Perfiles Profesionales
            professionals.forEach { professional ->
                val updatedProfessional = professional.copy(userId = userIdMap[professional.userId] ?: professional.userId)
                val result = IFirestoreService.saveData("professionals", "", updatedProfessional.toMap())
                result.getOrNull()?.let { newId -> professionalIdMap[professional.profileId] = newId }
            }

            val offers = Information.generateMockOffers(particulars, ICategoryRepository) // ✅ Pasamos `ICategoryRepository`

            // 🔹 5️⃣ Subir Ofertas con `publisherId` Correcto
            offers.forEach { offer ->
                val updatedOffer = offer.copy(publisherId = userIdMap[offer.publisherId] ?: offer.publisherId)
                IFirestoreService.saveData("offers", "", updatedOffer.toMap())
            }

            Timber.d("✅ Datos de prueba subidos exitosamente a Firestore.")

        } catch (e: Exception) {
            Timber.e(e, "❌ Error al subir datos simulados a Firestore.")
        }
    }
}
