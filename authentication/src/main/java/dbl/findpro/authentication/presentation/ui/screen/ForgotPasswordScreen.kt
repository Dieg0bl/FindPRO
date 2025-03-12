package dbl.findpro.authentication.presentation.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import dbl.findpro.authentication.presentation.viewmodel.AuthenticationViewModel

@Composable
fun ForgotPasswordScreen(navController: NavHostController) {
    val viewModel: AuthenticationViewModel = hiltViewModel()
    var email by remember { mutableStateOf("") }
    var showMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Recuperar Contraseña", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                viewModel.forgotPassword(email) { result ->
                    showMessage = if (result.isSuccess) {
                        "Se ha enviado un correo para restablecer tu contraseña."
                    } else {
                        "Error: ${result.exceptionOrNull()?.message}"
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enviar Enlace de Recuperación")
        }
        showMessage?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = it, style = MaterialTheme.typography.bodyLarge)
        }
    }
}
