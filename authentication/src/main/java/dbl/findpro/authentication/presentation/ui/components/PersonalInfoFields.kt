package dbl.findpro.authentication.presentation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import dbl.findpro.authentication.utils.ValidationUtils
import dbl.findpro.core.domain.model.userGroupsAndProfiles.User

@Composable
fun PersonalInfoFields(
    user: User,
    onUserChange: (User) -> Unit,
    password: String,
    confirmPassword: String,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    validate: Boolean
) {
    val nameError = remember(user.name, validate) {
        validate && !ValidationUtils.isValidName(user.name)
    }

    val emailError = remember(user.email, validate) {
        validate && !ValidationUtils.isValidEmail(user.email)
    }

    val passwordError = remember(password, validate) {
        validate && !ValidationUtils.isValidPassword(password)
    }

    val confirmPasswordError = remember(password, confirmPassword, validate) {
        validate && !ValidationUtils.doPasswordsMatch(password, confirmPassword)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Campo de nombre con validación
        OutlinedTextField(
            value = user.name,
            onValueChange = { onUserChange(user.copy(name = it)) },
            label = { Text("Nombre Completo") },
            isError = nameError,
            modifier = Modifier.fillMaxWidth()
        )
        if (nameError) {
            Text(
                text = "El nombre debe tener al menos 3 caracteres.",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        // Campo de correo electrónico con validación
        OutlinedTextField(
            value = user.email,
            onValueChange = { onUserChange(user.copy(email = it)) },
            label = { Text("Correo Electrónico") },
            isError = emailError,
            modifier = Modifier.fillMaxWidth()
        )
        if (emailError) {
            Text(
                text = "Introduce un correo válido.",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        // Campos de contraseña
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text("Contraseña") },
            isError = passwordError,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        if (passwordError) {
            Text(
                text = "La contraseña debe tener al menos 6 caracteres.",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = onConfirmPasswordChange,
            label = { Text("Confirmar Contraseña") },
            isError = confirmPasswordError,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        if (confirmPasswordError) {
            Text(
                text = "Las contraseñas no coinciden.",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
