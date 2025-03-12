package dbl.findpro.authentication.presentation.ui.screen

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
import dbl.findpro.authentication.presentation.ui.components.GoogleSignUpButton
import dbl.findpro.authentication.presentation.ui.components.UserRegistrationForm
import dbl.findpro.authentication.presentation.viewmodel.AuthenticationViewModel
import dbl.findpro.core.domain.model.userGroupsAndProfiles.ProfileType
import dbl.findpro.core.domain.model.userGroupsAndProfiles.User
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavHostController,
    viewModel: AuthenticationViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()

    var user by remember {
        mutableStateOf(
            User(
                userId = "",
                name = "",
                email = "",
                preferencesId = null,
                userGroupsIdsMap = emptyMap(),
                profileTypeInUse = ProfileType.UNDEFINED
            )
        )
    }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var acceptedTerms by remember { mutableStateOf(false) }
    var validate by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crear Cuenta") },
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
                UserRegistrationForm(
                    user = user,
                    onUserChange = { updatedUser -> user = updatedUser },
                    password = password,
                    confirmPassword = confirmPassword,
                    onPasswordChange = { password = it },
                    onConfirmPasswordChange = { confirmPassword = it },
                    onRegister = {
                        scope.launch {
                            isLoading = true
                            errorMessage = null

                            val cleanedUser = user.copy(
                                name = user.name.trim(),
                                email = user.email.trim().replace(" ", "")
                            )
                            val cleanedPassword = password.trim()

                            Timber.d("📩 Intentando registrar usuario con email limpio: '${cleanedUser.email}'")

                            val result = viewModel.register(cleanedUser, cleanedPassword)
                            isLoading = false

                            if (result.isSuccess) {
                                Timber.i("✅ Registro exitoso")

                                // Redirigir directamente después de un registro exitoso
                                navController.navigate("select_profile") {
                                    popUpTo("register") { inclusive = true }
                                }

                            } else {
                                errorMessage = result.exceptionOrNull()?.message ?: "Error desconocido en el registro"
                            }
                        }
                    },
                    acceptedTerms = acceptedTerms,
                    onTermsAcceptedChange = { acceptedTerms = it },
                    validate = validate,
                    onValidate = { validate = it }
                )

                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
                }

                errorMessage?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }

                Spacer(modifier = Modifier.height(24.dp))

                GoogleSignUpButton(
                    onClick = {
                        scope.launch {
                            isLoading = true
                            val result = viewModel.googleSignUp()
                            isLoading = false

                            if (result.isSuccess) {
                                Timber.i("✅ Registro exitoso con Google")

                                // Redirigir directamente después de un registro exitoso
                                navController.navigate("select_profile") {
                                    popUpTo("register") { inclusive = true }
                                }

                            } else {
                                errorMessage = result.exceptionOrNull()?.message ?: "Error en Google Sign-Up"
                            }
                        }
                    }
                )
            }
        }
    )
}
