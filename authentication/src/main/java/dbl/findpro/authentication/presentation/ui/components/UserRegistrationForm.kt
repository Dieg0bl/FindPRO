package dbl.findpro.authentication.presentation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import dbl.findpro.authentication.utils.ValidationUtils
import dbl.findpro.core.domain.model.userGroupsAndProfiles.User

@Composable
fun UserRegistrationForm(
    user: User,
    onUserChange: (User) -> Unit,
    password: String,
    confirmPassword: String,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onRegister: () -> Unit,
    acceptedTerms: Boolean,
    onTermsAcceptedChange: (Boolean) -> Unit,
    validate: Boolean,
    onValidate: (Boolean) -> Unit
) {
    val isFormValid = remember(user, password, confirmPassword, acceptedTerms) {
        user.name.isNotBlank() &&
                user.email.isNotBlank() &&
                password.length >= 6 &&
                password == confirmPassword &&
                acceptedTerms
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = user.name,
            onValueChange = { onUserChange(user.copy(name = it.trim())) },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = user.email,
            onValueChange = { onUserChange(user.copy(email = it.trim().replace(" ", ""))) },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { onPasswordChange(it) },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { onConfirmPasswordChange(it) },
            label = { Text("Confirmar Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = acceptedTerms,
                onCheckedChange = { onTermsAcceptedChange(it) }
            )
            Text(
                text = "Acepto los términos y condiciones",
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Button(
            onClick = {
                onValidate(true)
                if (isFormValid) {
                    onRegister()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = isFormValid
        ) {
            Text("Registrar")
        }
    }
}
