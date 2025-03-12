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
import dbl.findpro.userprofiles.presentation.ui.components.ParticularProfileForm
import dbl.findpro.userprofiles.presentation.viewmodel.ParticularProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivateParticularProfileScreen(
    navController: NavHostController,
    viewModel: ParticularProfileViewModel = hiltViewModel()
) {
    var particularProfile by remember { mutableStateOf(viewModel.getInitialProfile()) }
    var validate by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Activar Perfil Particular") },
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
                ParticularProfileForm(
                    profile = particularProfile,
                    onProfileChange = { particularProfile = it },
                    validate = validate,
                    onValidate = { validate = true },
                    onSaveProfile = TODO()
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        validate = true
                        if (viewModel.validateProfile(particularProfile)) {
                            viewModel.saveProfile(particularProfile)
                            navController.navigate("search_offers") {
                                popUpTo("activate_particular_profile") { inclusive = true }
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
