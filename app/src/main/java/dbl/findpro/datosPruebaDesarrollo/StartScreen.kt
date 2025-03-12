package dbl.findpro.datosPruebaDesarrollo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import dbl.findpro.R
import dbl.findpro.presentation.ui.navigation.Routes
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun StartScreen(
    navController: NavHostController,
    viewModel: StartViewModel = hiltViewModel()
) {
    LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(false) }
    var uploadResult by remember { mutableStateOf<String?>(null) }

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "Logo",
                    modifier = Modifier.size(100.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))

                when {
                    isLoading -> CircularProgressIndicator() // 🔄 Indicador de carga

                    else -> {
                        // 📦 Botón para subir datos y continuar
                        Button(
                            onClick = {
                                isLoading = true
                                uploadResult = null
                                coroutineScope.launch {
                                    try {
                                        viewModel.uploadTestData() // ✅ Corrección aquí
                                        uploadResult = "✅ Datos subidos exitosamente a Firestore"
                                        Timber.d(uploadResult)

                                        delay(2000) // ⏳ Espera 2 segundos
                                        navController.navigate(Routes.SearchProfessionals.route) {
                                            popUpTo(Routes.Start.route) { inclusive = true }
                                        }
                                    } catch (e: Exception) {
                                        uploadResult = "❌ Error al subir datos: ${e.message}"
                                        Timber.e(e, "Error al subir datos")
                                    }
                                    isLoading = false
                                }
                            }
                        ) {
                            Text("Subir Datos Simulados y Continuar")
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // 🆕 Botón para continuar sin subir datos
                        OutlinedButton(
                            onClick = {
                                navController.navigate(Routes.SearchProfessionals.route) {
                                    popUpTo(Routes.Start.route) { inclusive = true }
                                }
                                Timber.w("🚀 Continuando sin subir datos")
                            }
                        ) {
                            Text("Continuar Sin Subir Datos")
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        uploadResult?.let {
                            Text(
                                it,
                                color = if (it.startsWith("✅")) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        }
    }
}
