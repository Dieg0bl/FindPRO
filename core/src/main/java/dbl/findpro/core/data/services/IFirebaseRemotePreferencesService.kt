package dbl.findpro.core.data.services

interface IFirebaseRemotePreferencesService {
    /**
     * Recupera todas las preferencias remotas y las guarda en caché.
     */
    suspend fun fetchRemotePreferences(): Result<Unit>

    /**
     * Obtiene el valor de una preferencia remota dado su identificador (clave).
     *
     * @param key La clave de la preferencia.
     */
    suspend fun getRemotePreference(key: String): Result<Any?>
}
