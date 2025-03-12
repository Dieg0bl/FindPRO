package dbl.findpro.features.mapservice.data.services

import com.google.firebase.firestore.FirebaseFirestore
import dbl.findpro.core.domain.model.userGroupsAndProfiles.ProfessionalProfile
import kotlinx.coroutines.tasks.await
import timber.log.Timber

class FirestoreServiceImpl(private val firestore: FirebaseFirestore) {

    private val professionalsCollection = firestore.collection("professionals")

    suspend fun saveProfessional(professional: ProfessionalProfile): Boolean {
        return runCatching {
            professionalsCollection.document(professional.profileId).set(professional).await()
            Timber.d("✅ Profesional guardado en Firestore: ${professional.profileId}")
            true
        }.getOrElse {
            Timber.e(it, "❌ Error al guardar el profesional")
            false
        }
    }


    suspend fun getAllProfessionals(): List<ProfessionalProfile> {
        return runCatching {
            professionalsCollection.get().await().toObjects(ProfessionalProfile::class.java)
        }.getOrElse {
            Timber.e(it, "❌ Error al recuperar profesionales")
            emptyList()
        }
    }


}
