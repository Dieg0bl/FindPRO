package dbl.findpro.core.util

import android.content.Context
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteConfigManager @Inject constructor(
    private val remoteConfig: FirebaseRemoteConfig,
    @ApplicationContext private val context: Context
) {
    private val sharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE)

    suspend fun fetchPrivateMapboxToken(): String {
        return withContext(Dispatchers.IO) { // ✅ Se ejecuta en un hilo de fondo
            val cachedToken = sharedPreferences.getString("mapbox_private_token", null)
            if (cachedToken != null) return@withContext cachedToken

            try {
                remoteConfig.fetchAndActivate().await()
                val token = remoteConfig.getString("mapbox_private_token")
                if (token.isNotEmpty()) {
                    sharedPreferences.edit().putString("mapbox_private_token", token).apply()
                    Timber.d("✅ Token privado de Mapbox almacenado en caché")
                }
                return@withContext token
            } catch (e: Exception) {
                Timber.e("❌ Error al obtener el token privado: ${e.message}")
                return@withContext ""
            }
        }
    }
}
