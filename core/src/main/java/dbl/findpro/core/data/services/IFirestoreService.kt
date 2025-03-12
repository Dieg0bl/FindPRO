package dbl.findpro.core.data.services

interface IFirestoreService {
    suspend fun saveData(
        collection: String,
        documentId: String,
        data: Map<String, Any>
    ): Result<String>

    suspend fun getDocument(
        collection: String,
        documentId: String
    ): Result<Map<String, Any>>

    suspend fun getAllDocuments(
        collection: String
    ): Result<List<Map<String, Any>>>

    suspend fun deleteDocument(
        collection: String,
        documentId: String
    ): Result<Unit>

    suspend fun queryDocuments(
        collection: String,
        field: String,
        value: Any
    ): Result<List<Map<String, Any>>>

    suspend fun updateData(
        collection: String,
        documentId: String,
        data: Map<String, Any>
    ): Result<Boolean>

    suspend fun saveMultipleDocuments(
        collection: String,
        documents: List<Pair<String, Map<String, Any>>>
    ): Result<Unit>
}
