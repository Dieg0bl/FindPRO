package dbl.findpro.userprofiles.presentation.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import dbl.findpro.core.domain.model.userGroupsAndProfiles.ProfileType
import dbl.findpro.userprofiles.presentation.viewmodel.SelectProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectProfileScreen(
    navController: NavHostController,
    viewModel: SelectProfileViewModel = hiltViewModel()
) {
    val user by viewModel.currentUser.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("¿Con qué perfil continuar?") }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (user == null) {
                    CircularProgressIndicator()
                    Text("⏳ Cargando usuario...")
                } else {
                    Text("👤 Usuario: ${user!!.email}")

                    Spacer(modifier = Modifier.height(16.dp))

                    // Botón para perfil PARTICULAR
                    Button(onClick = {
                        val hasParticularProfile = user!!.userGroupsIdsMap.containsKey(ProfileType.PARTICULAR)
                        if (hasParticularProfile) {
                            // Navega a la pantalla principal del perfil PARTICULAR
                            navController.navigate("particular_profile_main")
                        } else {
                            // Navega a la pantalla de activación de perfil PARTICULAR
                            navController.navigate("activate_particular_profile")
                        }
                    }) {
                        Text("Perfil Particular")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Botón para perfil PROFESIONAL
                    Button(onClick = {
                        val hasProfessionalProfile = user!!.userGroupsIdsMap.containsKey(ProfileType.PROFESSIONAL)
                        if (hasProfessionalProfile) {
                            // Navega a la pantalla principal del perfil PROFESIONAL
                            navController.navigate("professional_profile_main")
                        } else {
                            // Navega a la pantalla de activación de perfil PROFESIONAL
                            navController.navigate("activate_professional_profile")
                        }
                    }) {
                        Text("Perfil Profesional")
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Opción para continuar sin seleccionar un perfil
                    TextButton(onClick = {
                        navController.navigate("search_professionals")
                    }) {
                        Text("➡️ Continuar sin completar datos")
                    }
                }
            }
        }
    )
}
