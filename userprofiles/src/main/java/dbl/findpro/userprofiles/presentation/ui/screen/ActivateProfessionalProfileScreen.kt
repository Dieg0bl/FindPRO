package dbl.findpro.userprofiles.presentation.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import dbl.findpro.userprofiles.presentation.ui.components.ProfessionalProfileForm
import dbl.findpro.userprofiles.presentation.viewmodel.ProfessionalProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivateProfessionalProfileScreen(
    navController: NavHostController,
    viewModel: ProfessionalProfileViewModel = hiltViewModel()
) {
    var professionalProfile by remember { mutableStateOf(viewModel.getInitialProfile()) }
    var validate by remember { mutableStateOf(false) }
    val categories = viewModel.categories // ✅ Se obtiene la lista de categorías del ViewModel

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Activar Perfil Profesional") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProfessionalProfileForm(
                    profile = professionalProfile,
                    onProfileChange = { professionalProfile = it },
                    validate = validate,
                    onValidate = { validate = true },
                    onSaveProfile = {
                        validate = true
                        if (viewModel.validateProfile(professionalProfile)) {
                            viewModel.saveProfile(professionalProfile)
                            navController.navigate("search_offers") {
                                popUpTo("activate_professional_profile") { inclusive = true }
                            }
                        }
                    },
                    categories = categories
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        validate = true
                        if (viewModel.validateProfile(professionalProfile)) {
                            viewModel.saveProfile(professionalProfile)
                            navController.navigate("search_offers") {
                                popUpTo("activate_professional_profile") { inclusive = true }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Activar Perfil")
                }
            }
        }
    )
}
