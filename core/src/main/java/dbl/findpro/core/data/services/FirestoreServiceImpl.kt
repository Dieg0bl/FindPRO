package dbl.findpro.core.data.services

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : IFirestoreService {

    override suspend fun saveData(
        collection: String,
        documentId: String,
        data: Map<String, Any>
    ): Result<String> = withContext(Dispatchers.IO) {
        try {
            val docRef = if (documentId.isEmpty()) {
                firestore.collection(collection).document() // Genera ID automáticamente
            } else {
                firestore.collection(collection).document(documentId)
            }
            docRef.set(data).await()
            Timber.d("✅ Documento guardado en $collection con ID: ${docRef.id}")
            Result.success(docRef.id)
        } catch (e: Exception) {
            Timber.e(e, "❌ Error al guardar documento en Firestore")
            Result.failure(e)
        }
    }

    override suspend fun getDocument(
        collection: String,
        documentId: String
    ): Result<Map<String, Any>> = withContext(Dispatchers.IO) {
        try {
            val snapshot = firestore.collection(collection)
                .document(documentId)
                .get()
                .await()
            snapshot.data?.let { Result.success(it) }
                ?: Result.failure(Exception("Documento no encontrado"))
        } catch (e: Exception) {
            Timber.e(e, "❌ Error al obtener documento de Firestore")
            Result.failure(e)
        }
    }

    override suspend fun getAllDocuments(
        collection: String
    ): Result<List<Map<String, Any>>> = withContext(Dispatchers.IO) {
        try {
            val snapshot = firestore.collection(collection).get().await()
            val documents = snapshot.documents.mapNotNull { it.data }
            Timber.d("✅ Obtenidos ${documents.size} documentos de $collection")
            Result.success(documents)
        } catch (e: Exception) {
            Timber.e(e, "❌ Error al obtener documentos de Firestore")
            Result.failure(e)
        }
    }

    override suspend fun deleteDocument(
        collection: String,
        documentId: String
    ): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            firestore.collection(collection)
                .document(documentId)
                .delete()
                .await()
            Timber.d("✅ Documento eliminado en $collection con ID: $documentId")
            Result.success(Unit)
        } catch (e: Exception) {
            Timber.e(e, "❌ Error al eliminar documento en Firestore")
            Result.failure(e)
        }
    }

    override suspend fun queryDocuments(
        collection: String,
        field: String,
        value: Any
    ): Result<List<Map<String, Any>>> = withContext(Dispatchers.IO) {
        try {
            val snapshot = firestore.collection(collection)
                .whereEqualTo(field, value)
                .get()
                .await()
            val documents = snapshot.documents.mapNotNull { it.data }
            Timber.d("✅ Consulta en $collection completada con ${documents.size} resultados")
            Result.success(documents)
        } catch (e: Exception) {
            Timber.e(e, "❌ Error en la consulta de Firestore")
            Result.failure(e)
        }
    }

    override suspend fun updateData(
        collection: String,
        documentId: String,
        data: Map<String, Any>
    ): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            firestore.collection(collection)
                .document(documentId)
                .update(data)
                .await()
            Timber.d("✅ Documento actualizado en $collection con ID: $documentId")
            Result.success(true)
        } catch (e: Exception) {
            Timber.e(e, "❌ Error al actualizar documento en Firestore")
            Result.failure(e)
        }
    }

    override suspend fun saveMultipleDocuments(
        collection: String,
        documents: List<Pair<String, Map<String, Any>>>
    ): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val batch = firestore.batch()
            documents.forEach { (documentId, data) ->
                if (documentId.isNotBlank()) {
                    val docRef = firestore.collection(collection).document(documentId)
                    batch.set(docRef, data)
                }
            }
            batch.commit().await()
            Timber.d("✅ Múltiples documentos guardados en $collection")
            Result.success(Unit)
        } catch (e: Exception) {
            Timber.e(e, "❌ Error al guardar múltiples documentos en Firestore")
            Result.failure(e)
        }
    }
}
