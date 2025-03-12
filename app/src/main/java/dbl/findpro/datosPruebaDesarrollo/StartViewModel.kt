package dbl.findpro.datosPruebaDesarrollo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val firebaseDataUploader: FirebaseDataUploader
) : ViewModel() {

    fun uploadTestData() {
        viewModelScope.launch {
            try {
                Timber.d("📤 Subiendo datos de prueba a Firestore...")
                firebaseDataUploader.uploadTestData()
                Timber.d("✅ Datos de prueba subidos correctamente")
            } catch (e: Exception) {
                Timber.e(e, "❌ Error al subir datos de prueba a Firestore")
            }
        }
    }
}
